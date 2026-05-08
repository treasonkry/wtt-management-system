<template>
  <div class="player-manage">
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
        <router-link v-if="isAdmin" to="/logs" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
            <path d="M14 2v6h6M16 13H8M16 17H8M10 9H8"/>
          </svg>
          <span>日志</span>
        </router-link>
        <router-link v-if="isAdmin" to="/matches" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4zM5 4h4M5 4l2 7M19 4h-4M19 4l-2 7"/>
          </svg>
          <span>比赛</span>
        </router-link>
        <router-link v-if="!isAdmin" to="/my-matches" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4zM5 4h4M5 4l2 7M19 4h-4M19 4l-2 7"/>
          </svg>
          <span>我的比赛</span>
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
          <h1>球员管理</h1>
          <p>管理系统中的所有球员信息</p>
        </div>
        <button v-if="isAdmin" class="btn-add" @click="openAddModal">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加球员
        </button>
      </header>

      <div class="table-container animate-fade-in-up stagger-1">
        <table class="player-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>球员</th>
              <th>用户名</th>
              <th>手机号</th>
              <th>积分</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(player, index) in players" :key="player.id" :class="['table-row', `stagger-${Math.min(index + 1, 5)}`]">
              <td class="id-cell">{{ player.id }}</td>
              <td class="player-cell">
                <div class="player-avatar">
                  {{ player.name.charAt(0).toUpperCase() }}
                </div>
                <span class="player-name">{{ player.name }}</span>
              </td>
              <td>{{ player.username }}</td>
              <td>{{ player.phone || '—' }}</td>
              <td class="points-cell">
                <span class="points-badge">{{ player.points }}</span>
              </td>
              <td class="actions-cell">
                <button v-if="isAdmin" class="action-btn edit" @click="openEditModal(player)" title="编辑">
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M11 4H4a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7"/>
                    <path d="M18.5 2.5a2.121 2.121 0 013 3L12 15l-4 1 1-4 9.5-9.5z"/>
                  </svg>
                </button>
                <button class="action-btn analyze" @click="analyzePlayer(player)" title="AI分析">
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
                  </svg>
                </button>
                <button v-if="isAdmin" class="action-btn delete" @click="confirmDelete(player)" title="删除">
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
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

    <Transition name="modal">
      <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
        <div class="modal-card animate-scale-in">
          <div class="modal-header">
            <h2>{{ isEditMode ? '编辑球员' : '添加球员' }}</h2>
            <button class="close-btn" @click="closeModal">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="6" x2="6" y2="18"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
              </svg>
            </button>
          </div>

          <form @submit.prevent="submitForm" class="player-form">
            <div class="form-row">
              <div class="input-group">
                <label>姓名</label>
                <div class="input-wrapper">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="8" r="5"/>
                    <path d="M3 21v-2a7 7 0 0114 0v2"/>
                  </svg>
                  <input v-model="formData.name" type="text" placeholder="球员姓名" required />
                </div>
              </div>
              <div class="input-group">
                <label>用户名</label>
                <div class="input-wrapper">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"/>
                    <circle cx="12" cy="7" r="4"/>
                  </svg>
                  <input v-model="formData.username" type="text" placeholder="登录用户名" required />
                </div>
              </div>
            </div>

            <div class="form-row">
              <div class="input-group">
                <label>密码</label>
                <div class="input-wrapper">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="11" width="18" height="11" rx="2"/>
                    <path d="M7 11V7a5 5 0 0110 0v4"/>
                  </svg>
                  <input
                    v-model="formData.password"
                    type="password"
                    :placeholder="isEditMode ? '留空则不修改' : '输入密码'"
                    :required="!isEditMode"
                  />
                </div>
              </div>
              <div class="input-group">
                <label>手机号</label>
                <div class="input-wrapper">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="5" y="2" width="14" height="20" rx="2"/>
                    <line x1="12" y1="18" x2="12" y2="18"/>
                  </svg>
                  <input v-model="formData.phone" type="text" placeholder="11位手机号" />
                </div>
              </div>
            </div>

            <div class="input-group full-width">
              <label>积分</label>
              <div class="input-wrapper points-input">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <polygon points="12,2 15.09,8.26 22,9.27 17,14.14 18.18,21.02 12,17.77 5.82,21.02 7,14.14 2,9.27 8.91,8.26"/>
                </svg>
                <input v-model.number="formData.points" type="number" placeholder="0" min="0" max="9999" />
              </div>
            </div>

            <div class="equipment-section-title">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="3" width="18" height="18" rx="2"/>
                <line x1="3" y1="12" x2="21" y2="12"/>
              </svg>
              器材配置
            </div>

            <div class="form-row">
              <div class="input-group">
                <label>球拍底板</label>
                <div class="input-wrapper">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="3" y="3" width="18" height="18" rx="2"/>
                    <line x1="3" y1="12" x2="21" y2="12"/>
                  </svg>
                  <input v-model="formData.blade" type="text" placeholder="如：蝴蝶 Flext ALC" />
                </div>
              </div>
              <div class="input-group">
                <label>正手胶皮</label>
                <div class="input-wrapper">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"/>
                    <path d="M12 8v8M8 12h8"/>
                  </svg>
                  <input v-model="formData.forehandRubber" type="text" placeholder="如：红双喜 NEO 狂飙3" />
                </div>
              </div>
            </div>

            <div class="input-group full-width">
              <label>反手胶皮</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10"/>
                  <path d="M8 12h8"/>
                </svg>
                <input v-model="formData.backhandRubber" type="text" placeholder="如：蝴蝶 Rozena" />
              </div>
            </div>

            <Transition name="fade">
              <div v-if="formMessage" :class="['message', formMessageType]">
                {{ formMessage }}
              </div>
            </Transition>

            <div class="modal-actions">
              <button type="button" class="btn-cancel" @click="closeModal">取消</button>
              <button type="submit" class="btn-submit" :disabled="submitting">
                <span v-if="submitting" class="loading-spinner"></span>
                <span v-else>{{ isEditMode ? '保存修改' : '添加球员' }}</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </Transition>

    <Transition name="modal">
      <div v-if="showDeleteConfirm" class="modal-overlay" @click.self="showDeleteConfirm = false">
        <div class="modal-card confirm-dialog animate-scale-in">
          <div class="confirm-icon">
            <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="12"/>
              <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
          </div>
          <h2>确认删除</h2>
          <p>确定要删除球员 <strong>{{ deleteTarget.name }}</strong> 吗？</p>
          <p class="warning">此操作将同时删除该球员的器材记录，且不可恢复。</p>
          <div class="modal-actions">
            <button class="btn-cancel" @click="showDeleteConfirm = false">取消</button>
            <button class="btn-danger" @click="executeDelete" :disabled="deleting">
              <span v-if="deleting" class="loading-spinner"></span>
              <span v-else>确认删除</span>
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()
const players = ref([])
const isAdmin = ref(false)

const showModal = ref(false)
const isEditMode = ref(false)
const submitting = ref(false)
const formMessage = ref('')
const formMessageType = ref('')
const formData = ref({
  id: null,
  name: '',
  username: '',
  password: '',
  phone: '',
  points: 0,
  blade: '',
  forehandRubber: '',
  backhandRubber: ''
})

const showDeleteConfirm = ref(false)
const deleteTarget = ref({})
const deleting = ref(false)

const fetchPlayers = async () => {
  try {
    const response = await api.getAllPlayers()
    if (response.data.success) {
      players.value = response.data.data
    }
  } catch (error) {
    console.error('获取球员列表失败:', error)
  }
}

const openAddModal = () => {
  if (!isAdmin.value) return
  isEditMode.value = false
  formData.value = { id: null, name: '', username: '', password: '', phone: '', points: 0, blade: '', forehandRubber: '', backhandRubber: '' }
  formMessage.value = ''
  formMessageType.value = ''
  showModal.value = true
}

const openEditModal = async (player) => {
  if (!isAdmin.value) return
  isEditMode.value = true
  formData.value = {
    id: player.id,
    name: player.name,
    username: player.username,
    password: '',
    phone: player.phone || '',
    points: player.points,
    blade: '',
    forehandRubber: '',
    backhandRubber: ''
  }
  formMessage.value = ''
  formMessageType.value = ''
  showModal.value = true

  try {
    const response = await api.getEquipment(player.name)
    if (response.data.success && response.data.data) {
      formData.value.blade = response.data.data.blade || ''
      formData.value.forehandRubber = response.data.data.forehandRubber || ''
      formData.value.backhandRubber = response.data.data.backhandRubber || ''
    }
  } catch (error) {
    console.error('获取器材信息失败:', error)
  }
}

const closeModal = () => {
  showModal.value = false
  formMessage.value = ''
}

const submitForm = async () => {
  if (formData.value.password.length > 0 && formData.value.password.length < 6) {
    formMessage.value = '密码长度不能少于6位'
    formMessageType.value = 'error'
    return
  }
  submitting.value = true
  formMessage.value = ''
  try {
    let response
    if (isEditMode.value) {
      const updateData = { ...formData.value }
      delete updateData.blade
      delete updateData.forehandRubber
      delete updateData.backhandRubber
      if (!updateData.password) {
        delete updateData.password
      }
      response = await api.updatePlayer(updateData)
    } else {
      const addData = { ...formData.value }
      delete addData.blade
      delete addData.forehandRubber
      delete addData.backhandRubber
      response = await api.addPlayer(addData)
    }
    if (response.data.success) {
      await api.saveEquipment({
        playerName: formData.value.name,
        blade: formData.value.blade,
        forehandRubber: formData.value.forehandRubber,
        backhandRubber: formData.value.backhandRubber
      })
      formMessage.value = isEditMode.value ? '更新成功' : '添加成功'
      formMessageType.value = 'success'
      setTimeout(() => {
        closeModal()
        fetchPlayers()
      }, 1000)
    } else {
      formMessage.value = response.data.message || '操作失败'
      formMessageType.value = 'error'
    }
  } catch (error) {
    formMessage.value = '操作失败，请检查网络'
    formMessageType.value = 'error'
  } finally {
    submitting.value = false
  }
}

const confirmDelete = (player) => {
  deleteTarget.value = player
  showDeleteConfirm.value = true
}

const executeDelete = async () => {
  deleting.value = true
  try {
    const response = await api.deletePlayer(deleteTarget.value.id)
    if (response.data.success) {
      showDeleteConfirm.value = false
      fetchPlayers()
    } else {
      alert(response.data.message || '删除失败')
    }
  } catch (error) {
    alert('删除失败，请检查网络')
  } finally {
    deleting.value = false
  }
}

const analyzePlayer = (player) => {
  sessionStorage.setItem('analyzePlayer', JSON.stringify(player))
  router.push('/chat')
}

const logout = () => {
  sessionStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  const userStr = sessionStorage.getItem('user')
  if (userStr) {
    const user = JSON.parse(userStr)
    isAdmin.value = user.type === 'admin'
  }
  fetchPlayers()
})
</script>

<style scoped>
.player-manage {
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
  max-width: 1200px;
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

.btn-add {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background: linear-gradient(135deg, var(--accent-secondary), #00e6b8);
  color: var(--bg-primary);
  border: none;
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 600;
}

.btn-add:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-secondary);
}

.table-container {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  overflow: hidden;
}

.player-table {
  width: 100%;
  border-collapse: collapse;
}

.player-table th {
  padding: 16px 20px;
  text-align: left;
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: var(--text-muted);
  background: var(--bg-secondary);
  border-bottom: 1px solid var(--border);
}

.player-table td {
  padding: 16px 20px;
  border-bottom: 1px solid var(--border);
  font-size: 14px;
}

.table-row {
  transition: background 0.2s;
}

.table-row:hover {
  background: var(--bg-secondary);
}

.table-row:last-child td {
  border-bottom: none;
}

.id-cell {
  color: var(--text-muted);
  font-family: var(--font-display);
  font-weight: 600;
}

.player-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.player-avatar {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  color: white;
  font-weight: 700;
  font-size: 14px;
  border-radius: var(--radius-sm);
}

.player-name {
  font-weight: 500;
  color: var(--text-primary);
}

.points-cell {
  font-family: var(--font-display);
}

.points-badge {
  display: inline-block;
  padding: 4px 12px;
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
  font-weight: 600;
  font-size: 13px;
  border-radius: 20px;
}

.actions-cell {
  display: flex;
  gap: 8px;
}

.action-btn {
  width: 34px;
  height: 34px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: var(--radius-sm);
  cursor: pointer;
  transition: all 0.2s;
}

.action-btn.edit {
  background: rgba(245, 158, 11, 0.15);
  color: var(--warning);
}

.action-btn.edit:hover {
  background: var(--warning);
  color: white;
}

.action-btn.analyze {
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
}

.action-btn.analyze:hover {
  background: var(--accent-primary);
  color: white;
}

.action-btn.delete {
  background: var(--danger-glow);
  color: var(--danger);
}

.action-btn.delete:hover {
  background: var(--danger);
  color: white;
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
}

.modal-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-xl);
  padding: 40px;
  width: 100%;
  max-width: 520px;
  box-shadow: var(--shadow-elevated);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.modal-header h2 {
  font-size: 22px;
}

.close-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-secondary);
  color: var(--text-secondary);
  border-radius: var(--radius-sm);
}

.close-btn:hover {
  background: var(--bg-elevated);
  color: var(--text-primary);
}

.player-form .form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.input-group {
  margin-bottom: 18px;
}

.input-group.full-width {
  grid-column: 1 / -1;
}

.input-group label {
  display: block;
  font-size: 13px;
  font-weight: 500;
  color: var(--text-secondary);
  margin-bottom: 8px;
}

.equipment-section-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 600;
  color: var(--accent-secondary);
  margin: 24px 0 16px 0;
  padding-bottom: 8px;
  border-bottom: 1px solid var(--border);
}

.equipment-section-title svg {
  opacity: 0.8;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-wrapper svg {
  position: absolute;
  left: 14px;
  color: var(--text-muted);
  pointer-events: none;
  transition: color 0.2s;
}

.input-wrapper:focus-within svg {
  color: var(--accent-primary);
}

.input-wrapper.points-input svg {
  color: var(--accent-primary);
}

.input-wrapper input {
  width: 100%;
  padding: 14px 14px 14px 46px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-primary);
  font-size: 15px;
  transition: all 0.25s;
}

.input-wrapper input::placeholder {
  color: var(--text-muted);
}

.input-wrapper input:focus {
  outline: none;
  border-color: var(--accent-primary);
  box-shadow: 0 0 0 3px var(--accent-primary-glow);
}

.message {
  margin-top: 16px;
  padding: 12px 16px;
  border-radius: var(--radius-sm);
  font-size: 14px;
  text-align: center;
}

.message.error {
  background: var(--danger-glow);
  color: var(--danger);
  border: 1px solid rgba(239, 68, 68, 0.3);
}

.message.success {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
  border: 1px solid rgba(0, 212, 170, 0.3);
}

.modal-actions {
  display: flex;
  gap: 12px;
  margin-top: 28px;
}

.btn-cancel {
  flex: 1;
  padding: 14px;
  background: var(--bg-secondary);
  color: var(--text-secondary);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
}

.btn-cancel:hover {
  background: var(--bg-elevated);
  color: var(--text-primary);
}

.btn-submit {
  flex: 1;
  padding: 14px;
  background: linear-gradient(135deg, var(--accent-primary), #ff8555);
  color: white;
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 600;
}

.btn-submit:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-primary);
}

.btn-submit:disabled,
.btn-danger:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.btn-danger {
  flex: 1;
  padding: 14px;
  background: var(--danger);
  color: white;
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 600;
}

.btn-danger:hover:not(:disabled) {
  box-shadow: var(--shadow-glow-primary);
}

.loading-spinner {
  display: inline-block;
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255,255,255,0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.confirm-dialog {
  text-align: center;
  max-width: 400px;
}

.confirm-icon {
  width: 64px;
  height: 64px;
  margin: 0 auto 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--danger-glow);
  color: var(--danger);
  border-radius: 50%;
}

.confirm-dialog h2 {
  margin-bottom: 12px;
}

.confirm-dialog p {
  margin-bottom: 8px;
}

.confirm-dialog strong {
  color: var(--text-primary);
}

.confirm-dialog .warning {
  color: var(--danger);
  font-size: 13px;
  margin-bottom: 24px;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.modal-enter-active {
  transition: opacity 0.3s ease;
}

.modal-leave-active {
  transition: opacity 0.2s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}
</style>
