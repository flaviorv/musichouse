import { useState, useEffect } from "react";
import Card from "./Card";
import "./CategoryGroup.css";

function GroupedProducts({ groupedProducts, nProductsToShow }) {
  const [pageByGroup, setPageByGroup] = useState({});
  const [productsPerPage, setProductsPerPage] = useState(nProductsToShow);

  const handleNext = (type, totalProducts) => {
    setPageByGroup((prev) => {
      const currentPage = prev[type] || 0;
      const maxPage = Math.floor((totalProducts - 1) / productsPerPage);
      return { ...prev, [type]: Math.min(currentPage + 1, maxPage) };
    });
  };

  const handlePrev = (type) => {
    setPageByGroup((prev) => {
      const currentPage = prev[type] || 0;
      return { ...prev, [type]: Math.max(currentPage - 1, 0) };
    });
  };

  useEffect(() => {
    function updateProductsPerPage() {
      if (window.innerWidth < 800) {
        setProductsPerPage(1);
      } else if (window.innerWidth < 1200) {
        setProductsPerPage(2);
      } else if (window.innerWidth < 1800) {
        setProductsPerPage(3);
      } else {
        setProductsPerPage(nProductsToShow);
      }
    }

    updateProductsPerPage();
    window.addEventListener("resize", updateProductsPerPage);

    return () => window.removeEventListener("resize", updateProductsPerPage);
  }, []);

  return (
    <>
      {Object.entries(groupedProducts).map(([type, products]) => {
        const currentPage = pageByGroup[type] || 0;
        let startIndex = currentPage * productsPerPage;
        let lastIndex = startIndex + productsPerPage;
        if (products.length < lastIndex) {
          startIndex = Math.max(products.length - productsPerPage, 0);
          lastIndex = products.length;
        }
        const visibleProducts = products.slice(startIndex, lastIndex);

        return (
          <section key={type} className="grouped-products">
            <div>
              <div className="pagination-controls">
                <button
                  onClick={() => handlePrev(type)}
                  disabled={currentPage === 0}
                  style={{
                    visibility:
                      products.length > productsPerPage ? "visible" : "hidden",
                  }}
                >
                  ◀
                </button>

                <h4 className="category-title">
                  {type.charAt(0).toUpperCase() + type.slice(1)}
                </h4>

                <button
                  onClick={() => handleNext(type, products.length)}
                  disabled={startIndex + productsPerPage >= products.length}
                  style={{
                    visibility:
                      products.length > productsPerPage ? "visible" : "hidden",
                  }}
                >
                  ▶
                </button>
              </div>
              <div className="products-grid">
                {visibleProducts.map((product) => (
                  <Card key={product.model} product={product} />
                ))}
              </div>
            </div>
          </section>
        );
      })}
    </>
  );
}

export default GroupedProducts;
