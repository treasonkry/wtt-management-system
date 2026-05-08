<template>
  <div class="player-home">
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
        <router-link to="/player" class="nav-item">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M3 9l9-7 9 7v11a2 2 0 01-2 2H5a2 2 0 01-2-2z"/>
            <polyline points="9,22 9,12 15,12 15,22"/>
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
        <router-link to="/my-matches" class="nav-item">
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

    <main class="main-content" v-if="player">
      <header class="page-header animate-fade-in-up">
        <div class="welcome-section">
          <div class="avatar">
            {{ player.name.charAt(0).toUpperCase() }}
          </div>
          <div class="welcome-text">
            <h1>欢迎，<span class="highlight">{{ player.name }}</span></h1>
            <p>用户名: {{ player.username }}</p>
          </div>
        </div>
      </header>

      <section class="stats-grid">
        <div class="stat-card animate-fade-in-up stagger-1">
          <div class="stat-icon points">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polygon points="12,2 15.09,8.26 22,9.27 17,14.14 18.18,21.02 12,17.77 5.82,21.02 7,14.14 2,9.27 8.91,8.26"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-label">当前积分</span>
            <span class="stat-value">{{ player.points || 0 }}</span>
          </div>
        </div>

        <div class="stat-card animate-fade-in-up stagger-2">
          <div class="stat-icon phone">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <rect x="5" y="2" width="14" height="20" rx="2"/>
              <line x1="12" y1="18" x2="12" y2="18"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-label">手机号</span>
            <span class="stat-value">{{ player.phone || '未设置' }}</span>
          </div>
        </div>
      </section>

      <section class="equipment-section animate-fade-in-up stagger-3">
        <div class="section-header">
          <h2>我的器材</h2>
          <button class="edit-btn" @click="showEquipmentModal = true">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M11 4H4a2 2 0 00-2 2v14a2 2 0 002 2h14a2 2 0 002-2v-7"/>
              <path d="M18.5 2.5a2.121 2.121 0 013 3L12 15l-4 1 1-4 9.5-9.5z"/>
            </svg>
            {{ equipment ? '编辑器材' : '添加器材' }}
          </button>
        </div>

        <div class="equipment-card" v-if="equipment">
          <div class="equipment-item">
            <div class="equipment-icon blade">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <rect x="3" y="3" width="18" height="18" rx="2"/>
                <line x1="3" y1="12" x2="21" y2="12"/>
              </svg>
            </div>
            <div class="equipment-info">
              <span class="equipment-label">球拍底板</span>
              <span class="equipment-value">{{ equipment.blade || '未设置' }}</span>
            </div>
          </div>
          <div class="equipment-item">
            <div class="equipment-icon forehand">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <path d="M12 8v8M8 12h8"/>
              </svg>
            </div>
            <div class="equipment-info">
              <span class="equipment-label">正手胶皮</span>
              <span class="equipment-value">{{ equipment.forehandRubber || '未设置' }}</span>
            </div>
          </div>
          <div class="equipment-item">
            <div class="equipment-icon backhand">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <path d="M8 12h8"/>
              </svg>
            </div>
            <div class="equipment-info">
              <span class="equipment-label">反手胶皮</span>
              <span class="equipment-value">{{ equipment.backhandRubber || '未设置' }}</span>
            </div>
          </div>
        </div>

        <div class="equipment-empty" v-else>
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <rect x="3" y="3" width="18" height="18" rx="2"/>
            <line x1="3" y1="12" x2="21" y2="12"/>
          </svg>
          <p>尚未配置器材</p>
          <button @click="showEquipmentModal = true">添加器材</button>
        </div>
      </section>
    </main>

    <div v-else class="loading-state">
      <div class="loading-spinner"></div>
      <p>加载中...</p>
    </div>

    <Transition name="modal">
      <div v-if="showEquipmentModal" class="modal-overlay" @click.self="showEquipmentModal = false">
        <div class="modal-card animate-scale-in">
          <div class="modal-header">
            <h2>{{ equipment ? '编辑器材' : '添加器材' }}</h2>
            <button class="close-btn" @click="showEquipmentModal = false">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="6" x2="6" y2="18"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
              </svg>
            </button>
          </div>

          <form @submit.prevent="saveEquipment" class="equipment-form">
            <div class="input-group">
              <label>球拍底板</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="3" width="18" height="18" rx="2"/>
                  <line x1="3" y1="12" x2="21" y2="12"/>
                </svg>
                <input v-model="equipmentForm.blade" type="text" placeholder="如：蝴蝶 Flext ALC" />
              </div>
            </div>
            <div class="input-group">
              <label>正手胶皮</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10"/>
                  <path d="M12 8v8M8 12h8"/>
                </svg>
                <input v-model="equipmentForm.forehandRubber" type="text" placeholder="如：红双喜 NEO 狂飙3" />
              </div>
            </div>
            <div class="input-group">
              <label>反手胶皮</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10"/>
                  <path d="M8 12h8"/>
                </svg>
                <input v-model="equipmentForm.backhandRubber" type="text" placeholder="如：蝴蝶 Rozena" />
              </div>
            </div>

            <Transition name="fade">
              <div v-if="message" :class="['message', messageType]">
                {{ message }}
              </div>
            </Transition>

            <div class="modal-actions">
              <button type="button" class="btn-cancel" @click="showEquipmentModal = false">取消</button>
              <button type="submit" class="btn-submit" :disabled="saving">
                <span v-if="saving" class="loading-spinner small"></span>
                <span v-else>保存</span>
              </button>
            </div>
          </form>
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
const player = ref(null)
const equipment = ref(null)
const showEquipmentModal = ref(false)
const saving = ref(false)
const message = ref('')
const messageType = ref('')

const equipmentForm = ref({
  blade: '',
  forehandRubber: '',
  backhandRubber: ''
})

onMounted(async () => {
  const userStr = sessionStorage.getItem('user')
  if (!userStr) {
    router.push('/login')
    return
  }

  const user = JSON.parse(userStr)
  if (user.type !== 'player') {
    router.push('/login')
    return
  }

  try {
    const response = await api.getPlayerInfo(user.username)
    if (response.data.success) {
      player.value = response.data.data
      await loadEquipment()
    }
  } catch (error) {
    console.error('获取球员信息失败:', error)
  }
})

const loadEquipment = async () => {
  if (!player.value) return
  try {
    const response = await api.getEquipment(player.value.name)
    if (response.data.success && response.data.data) {
      equipment.value = response.data.data
      equipmentForm.value = {
        blade: equipment.value.blade || '',
        forehandRubber: equipment.value.forehandRubber || '',
        backhandRubber: equipment.value.backhandRubber || ''
      }
    }
  } catch (error) {
    console.error('获取器材信息失败:', error)
  }
}

const saveEquipment = async () => {
  saving.value = true
  message.value = ''
  try {
    const response = await api.saveEquipment({
      playerName: player.value.name,
      blade: equipmentForm.value.blade,
      forehandRubber: equipmentForm.value.forehandRubber,
      backhandRubber: equipmentForm.value.backhandRubber
    })
    if (response.data.success) {
      message.value = '保存成功'
      messageType.value = 'success'
      equipment.value = {
        playerName: player.value.name,
        blade: equipmentForm.value.blade,
        forehandRubber: equipmentForm.value.forehandRubber,
        backhandRubber: equipmentForm.value.backhandRubber
      }
      setTimeout(() => {
        showEquipmentModal.value = false
        message.value = ''
      }, 1500)
    } else {
      message.value = response.data.message || '保存失败'
      messageType.value = 'error'
    }
  } catch (error) {
    message.value = '保存失败，请检查网络'
    messageType.value = 'error'
  } finally {
    saving.value = false
  }
}

const logout = () => {
  sessionStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.player-home {
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
  max-width: 900px;
  margin: 0 auto;
  padding: 48px 32px;
}

.page-header {
  margin-bottom: 40px;
}

.welcome-section {
  display: flex;
  align-items: center;
  gap: 24px;
}

.avatar {
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  color: white;
  font-family: var(--font-display);
  font-size: 32px;
  font-weight: 700;
  border-radius: var(--radius-lg);
}

.welcome-text h1 {
  font-size: 32px;
  margin-bottom: 8px;
}

.welcome-text .highlight {
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.welcome-text p {
  color: var(--text-muted);
  font-size: 14px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 24px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
}

.stat-icon {
  width: 52px;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-md);
}

.stat-icon.points {
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
}

.stat-icon.phone {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
}

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label {
  font-size: 13px;
  color: var(--text-muted);
}

.stat-value {
  font-family: var(--font-display);
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
}

.equipment-section {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-xl);
  padding: 32px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.section-header h2 {
  font-size: 20px;
}

.edit-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background: var(--bg-secondary);
  color: var(--text-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
}

.edit-btn:hover {
  background: var(--bg-elevated);
  color: var(--accent-primary);
  border-color: var(--accent-primary);
}

.equipment-card {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
}

.equipment-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: var(--bg-secondary);
  border-radius: var(--radius-md);
}

.equipment-icon {
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-sm);
}

.equipment-icon.blade {
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
}

.equipment-icon.forehand {
  background: rgba(239, 68, 68, 0.15);
  color: #ef4444;
}

.equipment-icon.backhand {
  background: rgba(59, 130, 246, 0.15);
  color: #3b82f6;
}

.equipment-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.equipment-label {
  font-size: 12px;
  color: var(--text-muted);
}

.equipment-value {
  font-size: 15px;
  font-weight: 500;
  color: var(--text-primary);
}

.equipment-empty {
  text-align: center;
  padding: 48px 20px;
  color: var(--text-muted);
}

.equipment-empty svg {
  opacity: 0.3;
  margin-bottom: 16px;
}

.equipment-empty p {
  margin-bottom: 16px;
}

.equipment-empty button {
  padding: 12px 24px;
  background: var(--accent-primary);
  color: white;
  border: none;
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
}

.equipment-empty button:hover {
  background: #ff8555;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  gap: 16px;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--border);
  border-top-color: var(--accent-primary);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

.loading-spinner.small {
  width: 18px;
  height: 18px;
  border-width: 2px;
}

@keyframes spin {
  to { transform: rotate(360deg); }
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
  max-width: 460px;
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

.input-group {
  margin-bottom: 18px;
}

.input-group label {
  display: block;
  font-size: 13px;
  font-weight: 500;
  color: var(--text-secondary);
  margin-bottom: 8px;
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
}

.input-wrapper input {
  width: 100%;
  padding: 14px 14px 14px 46px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-primary);
  font-size: 15px;
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

.btn-submit:disabled {
  opacity: 0.7;
  cursor: not-allowed;
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
