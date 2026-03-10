let count = 0;
let loadingCallback = null;
const MIN_DISPLAY_TIME = 600;

export const onLoadingChange = (cb) => {
  loadingCallback = cb;
};

export const setLoading = (isLoading) => {
  if (isLoading) {
    count++;
    if (loadingCallback) loadingCallback(true);
  } else {
    setTimeout(() => {
      count--;
      if (count <= 0 && loadingCallback) {
        count = 0;
        loadingCallback(false);
      }
    }, MIN_DISPLAY_TIME);
  }
};
