
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import CouponManager from "./components/listers/CouponCards"
import CouponDetail from "./components/listers/CouponDetail"

import PointManager from "./components/listers/PointCards"
import PointDetail from "./components/listers/PointDetail"


import DashboardView from "./components/DashboardView"
import DashboardViewDetail from "./components/DashboardViewDetail"

export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/coupons',
                name: 'CouponManager',
                component: CouponManager
            },
            {
                path: '/coupons/:id',
                name: 'CouponDetail',
                component: CouponDetail
            },

            {
                path: '/points',
                name: 'PointManager',
                component: PointManager
            },
            {
                path: '/points/:id',
                name: 'PointDetail',
                component: PointDetail
            },


            {
                path: '/dashboards',
                name: 'DashboardView',
                component: DashboardView
            },
            {
                path: '/dashboards/:id',
                name: 'DashboardViewDetail',
                component: DashboardViewDetail
            },


    ]
})
