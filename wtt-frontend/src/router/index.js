import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import AdminDashboard from '../views/AdminDashboard.vue'
import PlayerManage from '../views/PlayerManage.vue'
import ChatAI from '../views/ChatAI.vue'
import OperationLog from '../views/OperationLog.vue'
import PlayerHomepage from '../views/PlayerHomepage.vue'
import MatchManage from '../views/MatchManage.vue'
import MatchHistory from '../views/MatchHistory.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: AdminDashboard,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/players',
    name: 'PlayerManage',
    component: PlayerManage,
    meta: { requiresAuth: true }
  },
  {
    path: '/chat',
    name: 'ChatAI',
    component: ChatAI,
    meta: { requiresAuth: true }
  },
  {
    path: '/logs',
    name: 'OperationLog',
    component: OperationLog,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/player',
    name: 'PlayerHomepage',
    component: PlayerHomepage,
    meta: { requiresAuth: true }
  },
  {
    path: '/matches',
    name: 'MatchManage',
    component: MatchManage,
    meta: { requiresAuth: true, requiresAdmin: true }
  },
  {
    path: '/my-matches',
    name: 'MatchHistory',
    component: MatchHistory,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const isLoggedIn = sessionStorage.getItem('user')
  if (to.meta.requiresAuth && !isLoggedIn) {
    next('/login')
    return
  }

  if (isLoggedIn) {
    const user = JSON.parse(isLoggedIn)
    if (user.type === 'player' && to.meta.requiresAdmin && to.path !== '/players') {
      next('/player')
      return
    }
  }

  next()
})

export default router