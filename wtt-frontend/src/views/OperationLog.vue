<template>
  <div class="log-page">
    <nav class="navbar">
      <div class="nav-brand">
        <svg width="32" height="32" viewBox="0 0 48 48" fill="none">
          <circle cx="24" cy="24" r="22" stroke="url(#navLogoGradient)" stroke-width="2.5"/>
          <path d="M16 24C16 19.5817 19.5817 16 24 16V16C28.4183 16 32 19.5817 32 24V32C32 36.4183 28.4183 40 24 40V40C19.5817 40 16 36.4183 16 32V24Z" fill="url(#navLogoGradient)"/>
          <circle cx="24" cy="24" r="5" fill="var(--bg-card)"/>
          <defs>
            <linearGradient id="navLogoGradient" x1="0" y1="0" x2="48" y2="48">
              <stop stop-color="#ff6b35"/>
              <stop offset="1" stop-color="#00d4aa"/>
            </linearGradient>
          </defs>
        </svg>
        <span>WTT</span>
      </div>
      <div class="nav-links">
        <router-link to="/admin" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <rect x="3" y="3" width="7" height="7" rx="1"/>
            <rect x="14" y="3" width="7" height="7" rx="1"/>
            <rect x="3" y="14" width="7" height="7" rx="1"/>
            <rect x="14" y="14" width="7" height="7" rx="1"/>
          </svg>
          <span>主页</span>
        </router-link>
        <router-link to="/players" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="8" r="5"/>
            <path d="M3 21v-2a7 7 0 0114 0v2"/>
          </svg>
          <span>球员</span>
        </router-link>
        <router-link to="/chat" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
          </svg>
          <span>AI分析</span>
        </router-link>
        <router-link to="/logs" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
            <path d="M14 2v6h6M16 13H8M16 17H8M10 9H8"/>
          </svg>
          <span>日志</span>
        </router-link>
        <router-link to="/matches" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4zM5 4h4M5 4l2 7M19 4h-4M19 4l-2 7"/>
          </svg>
          <span>比赛</span>
        </router-link>
      </div>
      <button class="logout-btn" @click="logout">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M9 21H5a2 2 0 01-2-2V5a2 2 0 012-2h4"/>
          <polyline points="16,17 21,12 16,7"/>
          <line x1="21" y1="12" x2="9" y2="12"/>
        </svg>
        <span>退出</span>
      </button>
    </nav>

    <main class="main-content">
      <header class="page-header animate-fade-in-up">
        <div class="header-content">
          <h1>操作日志</h1>
          <p>记录系统中所有用户的操作行为</p>
        </div>
        <div class="log-count">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
            <path d="M14 2v6h6"/>
          </svg>
          <span>共 {{ total }} 条记录</span>
        </div>
      </header>

      <div class="filters-bar animate-fade-in-up stagger-1">
        <div class="filter-tabs">
          <button :class="{ active: filter === 'all' }" @click="filter = 'all'; fetchLogs()">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="3" y="3" width="7" height="7" rx="1"/>
              <rect x="14" y="3" width="7" height="7" rx="1"/>
              <rect x="3" y="14" width="7" height="7" rx="1"/>
              <rect x="14" y="14" width="7" height="7" rx="1"/>
            </svg>
            全部
          </button>
          <button :class="{ active: filter === 'username' }" @click="filter = 'username'">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"/>
              <circle cx="12" cy="7" r="4"/>
            </svg>
            按用户
          </button>
          <button :class="{ active: filter === 'module' }" @click="filter = 'module'">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="3" y="3" width="7" height="7"/>
              <rect x="14" y="3" width="7" height="7"/>
              <rect x="14" y="14" width="7" height="7"/>
              <rect x="3" y="14" width="7" height="7"/>
            </svg>
            按模块
          </button>
          <button :class="{ active: filter === 'action' }" @click="filter = 'action'">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polygon points="13,2 3,14 12,14 11,22 21,10 12,10"/>
            </svg>
            按操作
          </button>
        </div>

        <Transition name="fade">
          <div class="filter-input" v-if="filter !== 'all'">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="11" cy="11" r="8"/>
              <line x1="21" y1="21" x2="16.65" y2="16.65"/>
            </svg>
            <input
              v-model="filterValue"
              :placeholder="getFilterPlaceholder()"
              @keyup.enter="applyFilter"
            />
            <button @click="applyFilter">查询</button>
          </div>
        </Transition>
      </div>

      <div class="table-container animate-fade-in-up stagger-2">
        <table class="log-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>用户</th>
              <th>类型</th>
              <th>模块</th>
              <th>描述</th>
              <th>方法</th>
              <th>IP</th>
              <th>时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="log in logs" :key="log.id">
              <td class="id-cell">{{ log.id }}</td>
              <td class="user-cell">
                <div class="user-avatar">
                  {{ log.username.charAt(0).toUpperCase() }}
                </div>
                {{ log.username }}
              </td>
              <td>
                <span :class="['badge', log.userType]">
                  {{ log.userType === 'admin' ? '管理员' : '球员' }}
                </span>
              </td>
              <td class="module-cell">{{ log.module }}</td>
              <td class="desc-cell">{{ log.description }}</td>
              <td>
                <span :class="['method', log.requestMethod.toLowerCase()]">
                  {{ log.requestMethod }}
                </span>
              </td>
              <td class="ip-cell">{{ log.ipAddress }}</td>
              <td class="time-cell">{{ formatTime(log.createTime) }}</td>
              <td>
                <button class="del-btn" @click="deleteLog(log.id)" title="删除">
                  <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="3,6 5,6 21,6"/>
                    <path d="M19 6v14a2 2 0 01-2 2H7a2 2 0 01-2-2V6m3 0V4a2 2 0 012-2h4a2 2 0 012 2v2"/>
                  </svg>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()
const logs = ref([])
const total = ref(0)
const filter = ref('all')
const filterValue = ref('')

onMounted(() => {
  fetchLogs()
})

const fetchLogs = async () => {
  try {
    const response = await api.getAllLogs()
    if (response.data.success) {
      logs.value = response.data.data
      total.value = response.data.total
    }
  } catch (error) {
    console.error('获取日志失败:', error)
  }
}

const applyFilter = async () => {
  if (!filterValue.value) return
  try {
    let response
    if (filter.value === 'username') {
      response = await api.getLogsByUsername(filterValue.value)
    } else if (filter.value === 'module') {
      response = await api.getLogsByModule(filterValue.value)
    } else if (filter.value === 'action') {
      response = await api.getLogsByAction(filterValue.value)
    }
    if (response && response.data.success) {
      logs.value = response.data.data
      total.value = response.data.data.length
    }
  } catch (error) {
    console.error('查询失败:', error)
  }
}

const deleteLog = async (id) => {
  if (!confirm('确定要删除这条日志吗?')) return
  try {
    const response = await api.deleteLog(id)
    if (response.data.success) {
      alert('删除成功')
      fetchLogs()
    }
  } catch (error) {
    alert('删除失败')
  }
}

const getFilterPlaceholder = () => {
  if (filter.value === 'username') return '输入用户名'
  if (filter.value === 'module') return '模块名 (如 Player, Chat)'
  if (filter.value === 'action') return '操作类型 (如 LOGIN, QUERY)'
  return ''
}

const formatTime = (time) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

const logout = () => {
  sessionStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.log-page {
  min-height: 100vh;
  background: var(--bg-primary);
}

.navbar {
  position: sticky;
  top: 0;
  z-index: 100;
  display: flex;
  align-items: center;
  padding: 0 32px;
  height: 72px;
  background: var(--bg-card);
  border-bottom: 1px solid var(--border);
  backdrop-filter: blur(12px);
}

.nav-brand {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-right: 48px;
}

.nav-brand span {
  font-family: var(--font-display);
  font-size: 22px;
  font-weight: 800;
  letter-spacing: 2px;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.nav-links {
  display: flex;
  gap: 4px;
  flex: 1;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  color: var(--text-secondary);
  text-decoration: none;
  border-radius: var(--radius-sm);
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s;
}

.nav-item:hover {
  color: var(--text-primary);
  background: var(--bg-secondary);
}

.nav-item.router-link-active {
  color: var(--accent-primary);
  background: var(--accent-primary-glow);
}

.logout-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background: transparent;
  color: var(--text-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-sm);
  font-size: 14px;
  font-weight: 500;
}

.logout-btn:hover {
  color: var(--danger);
  border-color: var(--danger);
  background: var(--danger-glow);
}

.main-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 48px 32px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 32px;
}

.header-content h1 {
  font-size: 32px;
  margin-bottom: 8px;
}

.header-content p {
  color: var(--text-muted);
  font-size: 14px;
}

.log-count {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-secondary);
  font-size: 14px;
}

.log-count svg {
  color: var(--accent-primary);
}

.filters-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.filter-tabs {
  display: flex;
  gap: 8px;
}

.filter-tabs button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 18px;
  background: var(--bg-card);
  color: var(--text-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
}

.filter-tabs button:hover {
  color: var(--text-primary);
  border-color: var(--border-hover);
}

.filter-tabs button.active {
  background: var(--accent-primary);
  color: white;
  border-color: var(--accent-primary);
}

.filter-input {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 16px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
}

.filter-input svg {
  color: var(--text-muted);
}

.filter-input input {
  width: 220px;
  padding: 8px 0;
  background: transparent;
  border: none;
  color: var(--text-primary);
  font-size: 14px;
}

.filter-input input:focus {
  outline: none;
}

.filter-input input::placeholder {
  color: var(--text-muted);
}

.filter-input button {
  padding: 8px 16px;
  background: var(--accent-primary);
  color: white;
  border: none;
  border-radius: var(--radius-sm);
  font-size: 13px;
  font-weight: 500;
}

.filter-input button:hover {
  background: #ff8555;
}

.table-container {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  overflow: hidden;
}

.log-table {
  width: 100%;
  border-collapse: collapse;
}

.log-table th {
  padding: 16px 16px;
  text-align: left;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: var(--text-muted);
  background: var(--bg-secondary);
  border-bottom: 1px solid var(--border);
}

.log-table td {
  padding: 14px 16px;
  border-bottom: 1px solid var(--border);
  font-size: 14px;
}

.log-table tr:last-child td {
  border-bottom: none;
}

.log-table tr:hover {
  background: var(--bg-secondary);
}

.id-cell {
  color: var(--text-muted);
  font-family: var(--font-display);
  font-weight: 600;
  font-size: 13px;
}

.user-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-avatar {
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  color: white;
  font-weight: 600;
  font-size: 11px;
  border-radius: 6px;
}

.module-cell {
  color: var(--accent-secondary);
  font-weight: 500;
}

.desc-cell {
  max-width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.ip-cell {
  font-family: var(--font-display);
  font-size: 13px;
  color: var(--text-muted);
}

.time-cell {
  font-size: 13px;
  color: var(--text-muted);
}

.badge {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.badge.admin {
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
}

.badge.player {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
}

.method {
  display: inline-block;
  padding: 3px 8px;
  border-radius: 4px;
  font-size: 11px;
  font-weight: 700;
  font-family: var(--font-display);
}

.method.get {
  background: rgba(34, 197, 94, 0.15);
  color: var(--success);
}

.method.post {
  background: rgba(245, 158, 11, 0.15);
  color: var(--warning);
}

.method.put {
  background: rgba(59, 130, 246, 0.15);
  color: #3b82f6;
}

.method.delete {
  background: var(--danger-glow);
  color: var(--danger);
}

.del-btn {
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--danger-glow);
  color: var(--danger);
  border: none;
  border-radius: var(--radius-sm);
  cursor: pointer;
  transition: all 0.2s;
}

.del-btn:hover {
  background: var(--danger);
  color: white;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
