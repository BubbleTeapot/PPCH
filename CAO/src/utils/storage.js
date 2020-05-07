const _storage_prefix = '_ct_ll_';

function _translateKey(key) {
    return _storage_prefix + key;
}

const storage = {
    set(key, value) {
        localStorage.setItem(_translateKey(key),JSON.stringify(value));
    },
    get(key) {
        return JSON.parse(localStorage.getItem(_translateKey(key)));
    },
    remove(key) {
        localStorage.removeItem(_translateKey(key));
    }
}
export default storage;