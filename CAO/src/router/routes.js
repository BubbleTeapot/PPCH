const Login = () => import('../pages/Login/Login')
const Register = () => import('../pages/Register/Register')
const Home = () => import('../pages/Home/Home')
const Chart = () => import('../pages/Chart/Chart')
const NotFound = () => import('../pages/NotFound/NotFound')

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/chart',
    name: 'chart',
    component: Chart
  },
  {
    path: '/',
    component: Home
  },
  {
    path: '*',
    component: NotFound
  },
]
export default routes