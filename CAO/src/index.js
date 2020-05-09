import Vue from 'vue'
import App from './App.vue'
import {Input, InputNumber, Form, FormItem} from 'element-ui'
import router from './router'
import VueRouter from 'vue-router'
import './styles/common.less'
import './utils/evHandle'

Vue.config.productionTip = false

Vue.use(Input);
Vue.use(InputNumber);
Vue.use(Form);
Vue.use(FormItem);

Vue.use(VueRouter)
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
