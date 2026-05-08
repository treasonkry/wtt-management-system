<template>
  <div class="dashboard">
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
        <div class="greeting">
          <h1>欢迎回来，<span class="highlight">{{ username }}</span></h1>
          <p>今天是 {{ currentDate }}，祝您工作顺利</p>
        </div>
      </header>

      <section class="stats-grid">
        <div class="stat-card animate-fade-in-up stagger-1">
          <div class="stat-icon players">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="8" r="5"/>
              <path d="M3 21v-2a7 7 0 0114 0v2"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-label">球员总数</span>
            <span class="stat-value">{{ stats.playerCount || 0 }}</span>
          </div>
          <div class="stat-decoration"></div>
        </div>

        <div class="stat-card animate-fade-in-up stagger-2">
          <div class="stat-icon admins">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M12 4a4 4 0 100 8 4 4 0 000-8zM6 20v-2a6 6 0 0112 0v2"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-label">管理员</span>
            <span class="stat-value">{{ stats.adminCount || 0 }}</span>
          </div>
          <div class="stat-decoration"></div>
        </div>

        <div class="stat-card animate-fade-in-up stagger-3">
          <div class="stat-icon status">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M22 11.08V12a10 10 0 11-5.93-9.14"/>
              <polyline points="22,4 12,14.01 9,11.01"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-label">系统状态</span>
            <span class="stat-value online">在线</span>
          </div>
          <div class="stat-decoration"></div>
        </div>
      </section>

      <section class="quick-actions animate-fade-in-up stagger-4">
        <h2>快捷操作</h2>
        <div class="actions-grid">
          <button class="action-card" @click="$router.push('/players')">
            <div class="action-icon">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="8" r="5"/>
                <path d="M3 21v-2a7 7 0 0114 0v2"/>
                <line x1="12" y1="8" x2="12" y2="16"/>
                <line x1="9" y1="11" x2="15" y2="11"/>
              </svg>
            </div>
            <span>管理球员</span>
            <svg class="arrow" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="9,18 15,12 9,6"/>
            </svg>
          </button>
          <button class="action-card" @click="$router.push('/chat')">
            <div class="action-icon">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
              </svg>
            </div>
            <span>AI 分析</span>
            <svg class="arrow" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="9,18 15,12 9,6"/>
            </svg>
          </button>
          <button class="action-card" @click="$router.push('/logs')">
            <div class="action-icon">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M14 2H6a2 2 0 00-2 2v16a2 2 0 002 2h12a2 2 0 002-2V8z"/>
                <path d="M14 2v6h6M16 13H8M16 17H8M10 9H8"/>
              </svg>
            </div>
            <span>查看日志</span>
            <svg class="arrow" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="9,18 15,12 9,6"/>
            </svg>
          </button>
          <button class="action-card" @click="openMatchModal">
            <div class="action-icon match">
              <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4zM5 4h4M5 4l2 7M19 4h-4M19 4l-2 7"/>
              </svg>
            </div>
            <span>举办比赛</span>
            <svg class="arrow" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="9,18 15,12 9,6"/>
            </svg>
          </button>
        </div>
      </section>
    </main>

    <Transition name="modal">
      <div v-if="showMatchModal" class="modal-overlay" @click.self="closeMatchModal">
        <div class="modal-card animate-scale-in">
          <div class="modal-header">
            <h2>举办比赛</h2>
            <button class="close-btn" @click="closeMatchModal">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="6" x2="6" y2="18"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
              </svg>
            </button>
          </div>

          <form @submit.prevent="submitMatch" class="match-form">
            <div class="form-row">
              <div class="input-group">
                <label>球员1</label>
                <select v-model="matchForm.player1Id" required @change="onPlayer1Change">
                  <option value="">选择球员</option>
                  <option v-for="player in players" :key="player.id" :value="player.id">
                    {{ player.name }}
                  </option>
                </select>
              </div>
              <div class="input-group">
                <label>球员2</label>
                <select v-model="matchForm.player2Id" required @change="onPlayer2Change">
                  <option value="">选择球员</option>
                  <option v-for="player in players" :key="player.id" :value="player.id">
                    {{ player.name }}
                  </option>
                </select>
              </div>
            </div>

            <div class="input-group">
              <label>大比分 (如: 3-2)</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4z"/>
                </svg>
                <input v-model="matchForm.largeScore" type="text" placeholder="3-2" required />
              </div>
            </div>

            <div class="input-group">
              <label>每局小比分 (用逗号分隔，如: 11:9,8:11,11:5,9:11,11:8)</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <line x1="8" y1="6" x2="21" y2="6"/>
                  <line x1="8" y1="12" x2="21" y2="12"/>
                  <line x1="8" y1="18" x2="21" y2="18"/>
                  <line x1="3" y1="6" x2="3.01" y2="6"/>
                  <line x1="3" y1="12" x2="3.01" y2="12"/>
                  <line x1="3" y1="18" x2="3.01" y2="18"/>
                </svg>
                <input v-model="matchForm.smallScores" type="text" placeholder="11:9,8:11,11:5,9:11,11:8" />
              </div>
            </div>

            <div class="input-group">
              <label>获胜者</label>
              <select v-model="matchForm.winnerId" required>
                <option value="">选择获胜者</option>
                <option v-if="matchForm.player1Id" :value="matchForm.player1Id">
                  {{ getPlayerName(matchForm.player1Id) }}
                </option>
                <option v-if="matchForm.player2Id" :value="matchForm.player2Id">
                  {{ getPlayerName(matchForm.player2Id) }}
                </option>
              </select>
            </div>

            <Transition name="fade">
              <div v-if="formMessage" :class="['message', formMessageType]">
                {{ formMessage }}
              </div>
            </Transition>

            <div class="modal-actions">
              <button type="button" class="btn-cancel" @click="closeMatchModal">取消</button>
              <button type="submit" class="btn-submit" :disabled="submitting">
                <span v-if="submitting" class="loading-spinner"></span>
                <span v-else>创建比赛</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()
const username = ref('')
const stats = ref({})
const players = ref([])

const showMatchModal = ref(false)
const submitting = ref(false)
const formMessage = ref('')
const formMessageType = ref('')
const matchForm = ref({
  player1Id: '',
  player2Id: '',
  largeScore: '',
  smallScores: '',
  winnerId: ''
})

const currentDate = computed(() => {
  const now = new Date()
  return now.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long'
  })
})

onMounted(async () => {
  const user = JSON.parse(sessionStorage.getItem('user') || '{}')
  username.value = user.username || 'Admin'
  try {
    const response = await api.getAdminMain()
    if (response.data.admin) {
      stats.value = {
        playerCount: response.data.playerCount,
        adminCount: response.data.adminCount
      }
    }
  } catch (error) {
    console.error('获取数据失败:', error)
  }
  await fetchPlayers()
})

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

const getPlayerName = (playerId) => {
  const player = players.value.find(p => p.id === playerId)
  return player ? player.name : ''
}

const onPlayer1Change = () => {
  if (matchForm.value.player1Id === matchForm.value.player2Id) {
    matchForm.value.player2Id = ''
  }
  matchForm.value.winnerId = ''
}

const onPlayer2Change = () => {
  if (matchForm.value.player2Id === matchForm.value.player1Id) {
    matchForm.value.player1Id = ''
  }
  matchForm.value.winnerId = ''
}

const openMatchModal = () => {
  matchForm.value = { player1Id: '', player2Id: '', largeScore: '', smallScores: '', winnerId: '' }
  formMessage.value = ''
  formMessageType.value = ''
  showMatchModal.value = true
}

const closeMatchModal = () => {
  showMatchModal.value = false
  formMessage.value = ''
}

const submitMatch = async () => {
  if (!matchForm.value.player1Id || !matchForm.value.player2Id) {
    formMessage.value = '请选择两名球员'
    formMessageType.value = 'error'
    return
  }
  if (matchForm.value.player1Id === matchForm.value.player2Id) {
    formMessage.value = '两名球员不能相同'
    formMessageType.value = 'error'
    return
  }
  if (!matchForm.value.largeScore) {
    formMessage.value = '请输入大比分'
    formMessageType.value = 'error'
    return
  }
  if (!matchForm.value.winnerId) {
    formMessage.value = '请选择获胜者'
    formMessageType.value = 'error'
    return
  }

  submitting.value = true
  formMessage.value = ''
  try {
    const matchData = {
      player1Id: parseInt(matchForm.value.player1Id),
      player2Id: parseInt(matchForm.value.player2Id),
      player1Name: getPlayerName(matchForm.value.player1Id),
      player2Name: getPlayerName(matchForm.value.player2Id),
      largeScore: matchForm.value.largeScore,
      smallScores: matchForm.value.smallScores || '',
      winnerId: parseInt(matchForm.value.winnerId),
      winnerName: getPlayerName(matchForm.value.winnerId)
    }
    const response = await api.addMatch(matchData)
    if (response.data.success) {
      formMessage.value = '比赛创建成功'
      formMessageType.value = 'success'
      setTimeout(() => {
        closeMatchModal()
      }, 1000)
    } else {
      formMessage.value = response.data.message || '创建失败'
      formMessageType.value = 'error'
    }
  } catch (error) {
    formMessage.value = '创建失败，请检查网络'
    formMessageType.value = 'error'
  } finally {
    submitting.value = false
  }
}

const logout = () => {
  sessionStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.dashboard {
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

.nav-item svg {
  opacity: 0.7;
}

.nav-item.router-link-active svg,
.nav-item:hover svg {
  opacity: 1;
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
  margin-bottom: 48px;
}

.greeting h1 {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 8px;
}

.greeting .highlight {
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.greeting p {
  font-size: 15px;
  color: var(--text-muted);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  margin-bottom: 48px;
}

.stat-card {
  position: relative;
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 28px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  overflow: hidden;
  transition: all 0.3s;
}

.stat-card:hover {
  border-color: var(--border-hover);
  transform: translateY(-4px);
  box-shadow: var(--shadow-card);
}

.stat-icon {
  width: 56px;
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-md);
  flex-shrink: 0;
}

.stat-icon.players {
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
}

.stat-icon.admins {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
}

.stat-icon.status {
  background: rgba(34, 197, 94, 0.15);
  color: var(--success);
}

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label {
  font-size: 13px;
  color: var(--text-muted);
  font-weight: 500;
}

.stat-value {
  font-family: var(--font-display);
  font-size: 32px;
  font-weight: 700;
  color: var(--text-primary);
  line-height: 1;
}

.stat-value.online {
  color: var(--success);
  font-size: 20px;
}

.stat-decoration {
  position: absolute;
  right: -20px;
  bottom: -20px;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  opacity: 0.1;
}

.stat-icon.players + .stat-info + .stat-decoration {
  background: var(--accent-primary);
}

.stat-icon.admins + .stat-info + .stat-decoration {
  background: var(--accent-secondary);
}

.quick-actions {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  padding: 32px;
}

.quick-actions h2 {
  font-size: 20px;
  margin-bottom: 24px;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 16px;
}

.action-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-primary);
  text-align: left;
  position: relative;
  overflow: hidden;
}

.action-card::before {
  content: '';
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, var(--accent-primary-glow), transparent);
  opacity: 0;
  transition: opacity 0.3s;
}

.action-card:hover::before {
  opacity: 1;
}

.action-card:hover {
  border-color: var(--accent-primary);
  transform: translateX(4px);
}

.action-icon {
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-elevated);
  border-radius: var(--radius-sm);
  color: var(--accent-primary);
  flex-shrink: 0;
  position: relative;
  z-index: 1;
}

.action-card span {
  flex: 1;
  font-size: 15px;
  font-weight: 500;
  position: relative;
  z-index: 1;
}

.arrow {
  color: var(--text-muted);
  transition: all 0.2s;
  position: relative;
  z-index: 1;
}

.action-card:hover .arrow {
  color: var(--accent-primary);
  transform: translateX(4px);
}

.action-icon.match {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
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
  border: none;
  cursor: pointer;
}

.close-btn:hover {
  background: var(--bg-elevated);
  color: var(--text-primary);
}

.match-form .form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
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

.input-group select {
  width: 100%;
  padding: 14px 14px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-primary);
  font-size: 15px;
  cursor: pointer;
}

.input-group select:focus {
  outline: none;
  border-color: var(--accent-primary);
  box-shadow: 0 0 0 3px var(--accent-primary-glow);
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
  border: none;
  cursor: pointer;
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
  border: none;
  cursor: pointer;
}

.btn-submit:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-primary);
}

.btn-submit:disabled {
  opacity: 0.7;
  cursor: not-allowed;
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

.animate-scale-in {
  animation: scaleIn 0.3s ease forwards;
}

@keyframes scaleIn {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
