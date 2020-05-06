import Login from '../pages/Login/Login'
import Home from '../pages/Home/Home'
import Chart from '../pages/Chart/Chart'
import NotFound from '../pages/NotFound/NotFound'

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login,
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