import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import SettingView from '../views/SettingView.vue'
import SearchView from '../views/SearchView.vue'
import ChatView from '../views/ChatView.vue'
import OnlineView from '../views/OnlineView.vue'

const routes = [
  {
    path: '/',
    redirect: "/home"
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },{
    path: '/setting',
    name: 'setting',
    component: SettingView
  },{
    path: '/search',
    name: 'search',
    component: SearchView
  },{
    path: '/chat',
    name: 'chat',
    component: ChatView
  },{
    path: '/online',
    name: 'online',
    component: OnlineView
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
