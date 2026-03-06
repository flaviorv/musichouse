let loadingCallback = null;
let count = 0;

export const onLoadingChange = (cb) => {
  loadingCallback = cb;
};

export const setLoading = (isLoading) => {
  if (isLoading) count++;
  else count--;
  console.log(count);
  if (loadingCallback) {
    loadingCallback(count > 0);
  }
};
