import Vue from 'vue'

function getDoc() {
  let doc = document.documentElement;
  return function(callback) {
    doc.addEventListener('click', callback);
    return function () {
      doc.removeEventListener('click', callback);
    }
  }
}
Vue.prototype.docLs = getDoc();