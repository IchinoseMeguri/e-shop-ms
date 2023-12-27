import { createRouter, createWebHashHistory } from 'vue-router'
import HomePage from '../components/HomePage.vue'
import Shopping from '../components/Shopping.vue'
import ShoppingCart from '../components/ShoppingCart.vue'
import UserInfo from '../components/UserInfo.vue'
import MyOrder from '../components/MyOrder.vue'
import Commodity from '../components/Commodity.vue'
import User from '../components/User.vue'
import Order from '../components/Order.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import OrderDetail from '../components/OrderDetail.vue'

const routes = [
    {
        path: '/',
        component: Login
    },
    {
        path: '/register',
        component: Register
    },
    {
        path: '/menu',
        component: HomePage,
        redirect: '/menu/shopping',
        children: [
            {
                path: 'shopping',
                component: Shopping
            },
            {
                path: 'shoppingcart',
                component: ShoppingCart
            },
            {
                path: 'userinfo',
                component: UserInfo
            },
            {
                path: 'myorder',
                component: MyOrder
            },
            {
                path: 'commodity',
                component: Commodity
            },
            {
                path: 'user',
                component: User
            },
            {
                path: 'order',
                component: Order
            },
            {
                path: 'orderdetail',
                component: OrderDetail
            },
        ]
    },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router;
