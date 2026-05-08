<template>
  <div class="match-manage">
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
          <h1>比赛管理</h1>
          <p>管理系统中的所有比赛记录</p>
        </div>
        <button class="btn-add" @click="openAddModal">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="5" x2="12" y2="19"/>
            <line x1="5" y1="12" x2="19" y2="12"/>
          </svg>
          添加比赛
        </button>
      </header>

      <div class="table-container animate-fade-in-up stagger-1">
        <table class="match-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>球员1</th>
              <th>球员2</th>
              <th>大比分</th>
              <th>小比分</th>
              <th>获胜者</th>
              <th>比赛时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(match, index) in matches" :key="match.id" :class="['table-row', `stagger-${Math.min(index + 1, 5)}`]">
              <td class="id-cell">{{ match.id }}</td>
              <td class="player-cell">
                <div class="player-info">
                  <div class="player-avatar">{{ match.player1Name.charAt(0).toUpperCase() }}</div>
                  <span>{{ match.player1Name }}</span>
                </div>
              </td>
              <td class="player-cell">
                <div class="player-info">
                  <div class="player-avatar">{{ match.player2Name.charAt(0).toUpperCase() }}</div>
                  <span>{{ match.player2Name }}</span>
                </div>
              </td>
              <td class="score-cell">
                <span class="large-score">{{ match.largeScore }}</span>
              </td>
              <td class="small-scores-cell">
                <div class="small-scores-list">
                  <span v-for="(score, i) in parseSmallScores(match.smallScores)" :key="i" class="score-badge">
                    {{ score }}
                  </span>
                </div>
              </td>
              <td>
                <span class="winner-badge" v-if="match.winnerName">{{ match.winnerName }}</span>
              </td>
              <td class="time-cell">{{ formatTime(match.createdAt) }}</td>
              <td class="actions-cell">
                <button class="action-btn delete" @click="confirmDelete(match)" title="删除">
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polyline points="3,6 5,6 21,6"/>
                    <path d="M19 6v14a2 2 0 01-2 2H7a2 2 0 01-2-2V6m3 0V4a2 2 0 012-2h4a2 2 0 012 2v2"/>
                  </svg>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div v-if="matches.length === 0" class="empty-state">
          <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4zM5 4h4M5 4l2 7M19 4h-4M19 4l-2 7"/>
          </svg>
          <p>暂无比赛记录</p>
        </div>
      </div>
    </main>

    <Transition name="modal">
      <div v-if="showAddModal" class="modal-overlay" @click.self="closeAddModal">
        <div class="modal-card animate-scale-in">
          <div class="modal-header">
            <h2>添加比赛</h2>
            <button class="close-btn" @click="closeAddModal">
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
                <select v-model="formData.player1Id" required @change="onPlayer1Change">
                  <option value="">选择球员</option>
                  <option v-for="player in players" :key="player.id" :value="player.id">
                    {{ player.name }}
                  </option>
                </select>
              </div>
              <div class="input-group">
                <label>球员2</label>
                <select v-model="formData.player2Id" required @change="onPlayer2Change">
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
                <input v-model="formData.largeScore" type="text" placeholder="3-2" required />
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
                <input v-model="formData.smallScores" type="text" placeholder="11:9,8:11,11:5,9:11,11:8" />
              </div>
            </div>

            <div class="input-group">
              <label>获胜者</label>
              <select v-model="formData.winnerId" required>
                <option value="">选择获胜者</option>
                <option v-if="formData.player1Id" :value="formData.player1Id">
                  {{ getPlayerName(formData.player1Id) }}
                </option>
                <option v-if="formData.player2Id" :value="formData.player2Id">
                  {{ getPlayerName(formData.player2Id) }}
                </option>
              </select>
            </div>

            <Transition name="fade">
              <div v-if="formMessage" :class="['message', formMessageType]">
                {{ formMessage }}
              </div>
            </Transition>

            <div class="modal-actions">
              <button type="button" class="btn-cancel" @click="closeAddModal">取消</button>
              <button type="submit" class="btn-submit" :disabled="submitting">
                <span v-if="submitting" class="loading-spinner"></span>
                <span v-else>创建比赛</span>
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
          <p>确定要删除这场比赛记录吗？</p>
          <p class="warning">此操作不可恢复。</p>
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
const matches = ref([])
const players = ref([])
const isAdmin = ref(false)

const showAddModal = ref(false)
const submitting = ref(false)
const formMessage = ref('')
const formMessageType = ref('')
const formData = ref({
  player1Id: '',
  player2Id: '',
  largeScore: '',
  smallScores: '',
  winnerId: ''
})

const showDeleteConfirm = ref(false)
const deleteTarget = ref({})
const deleting = ref(false)

const fetchMatches = async () => {
  try {
    const response = await api.getAllMatches()
    if (response.data.success) {
      matches.value = response.data.data
    }
  } catch (error) {
    console.error('获取比赛列表失败:', error)
  }
}

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

const parseSmallScores = (smallScores) => {
  if (!smallScores) return []
  return smallScores.split(',').map(s => s.trim()).filter(s => s)
}

const formatTime = (time) => {
  if (!time) return '—'
  const date = new Date(time)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getPlayerName = (playerId) => {
  const player = players.value.find(p => p.id === playerId)
  return player ? player.name : ''
}

const onPlayer1Change = () => {
  if (formData.value.player1Id === formData.value.player2Id) {
    formData.value.player2Id = ''
  }
  formData.value.winnerId = ''
}

const onPlayer2Change = () => {
  if (formData.value.player2Id === formData.value.player1Id) {
    formData.value.player1Id = ''
  }
  formData.value.winnerId = ''
}

const openAddModal = () => {
  formData.value = { player1Id: '', player2Id: '', largeScore: '', smallScores: '', winnerId: '' }
  formMessage.value = ''
  formMessageType.value = ''
  showAddModal.value = true
}

const closeAddModal = () => {
  showAddModal.value = false
  formMessage.value = ''
}

const submitMatch = async () => {
  if (!formData.value.player1Id || !formData.value.player2Id) {
    formMessage.value = '请选择两名球员'
    formMessageType.value = 'error'
    return
  }
  if (formData.value.player1Id === formData.value.player2Id) {
    formMessage.value = '两名球员不能相同'
    formMessageType.value = 'error'
    return
  }
  if (!formData.value.largeScore) {
    formMessage.value = '请输入大比分'
    formMessageType.value = 'error'
    return
  }
  if (!formData.value.winnerId) {
    formMessage.value = '请选择获胜者'
    formMessageType.value = 'error'
    return
  }

  submitting.value = true
  formMessage.value = ''
  try {
    const matchData = {
      player1Id: parseInt(formData.value.player1Id),
      player2Id: parseInt(formData.value.player2Id),
      player1Name: getPlayerName(formData.value.player1Id),
      player2Name: getPlayerName(formData.value.player2Id),
      largeScore: formData.value.largeScore,
      smallScores: formData.value.smallScores || '',
      winnerId: parseInt(formData.value.winnerId),
      winnerName: getPlayerName(formData.value.winnerId)
    }
    const response = await api.addMatch(matchData)
    if (response.data.success) {
      formMessage.value = '比赛创建成功'
      formMessageType.value = 'success'
      setTimeout(() => {
        closeAddModal()
        fetchMatches()
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

const confirmDelete = (match) => {
  deleteTarget.value = match
  showDeleteConfirm.value = true
}

const executeDelete = async () => {
  deleting.value = true
  try {
    const response = await api.deleteMatch(deleteTarget.value.id)
    if (response.data.success) {
      showDeleteConfirm.value = false
      fetchMatches()
    } else {
      alert(response.data.message || '删除失败')
    }
  } catch (error) {
    alert('删除失败，请检查网络')
  } finally {
    deleting.value = false
  }
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
  fetchMatches()
  fetchPlayers()
})
</script>

<style scoped>
.match-manage {
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
  cursor: pointer;
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
  cursor: pointer;
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

.match-table {
  width: 100%;
  border-collapse: collapse;
}

.match-table th {
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

.match-table td {
  padding: 16px 20px;
  border-bottom: 1px solid var(--border);
  font-size: 14px;
  vertical-align: middle;
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
  display: table-cell;
}

.player-cell .player-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.player-avatar {
  width: 32px;
  height: 32px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  color: white;
  font-weight: 700;
  font-size: 12px;
  border-radius: var(--radius-sm);
  flex-shrink: 0;
}

.score-cell {
  text-align: center;
}

.score-cell .large-score {
  font-family: var(--font-display);
  font-size: 18px;
  font-weight: 700;
  color: var(--accent-primary);
}

.small-scores-cell {
  overflow: hidden;
}

.small-scores-list {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.score-badge {
  display: inline-block;
  padding: 2px 8px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: 4px;
  font-size: 12px;
  font-family: var(--font-display);
}

.winner-badge {
  display: inline-block;
  padding: 4px 12px;
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
  font-weight: 600;
  font-size: 13px;
  border-radius: 20px;
}

.time-cell {
  color: var(--text-muted);
  font-size: 13px;
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

.action-btn.delete {
  background: var(--danger-glow);
  color: var(--danger);
}

.action-btn.delete:hover {
  background: var(--danger);
  color: white;
}

.empty-state {
  padding: 60px 20px;
  text-align: center;
  color: var(--text-muted);
}

.empty-state svg {
  margin-bottom: 16px;
  opacity: 0.5;
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
  border: none;
  cursor: pointer;
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

.animate-fade-in-up {
  animation: fadeInUp 0.5s ease forwards;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
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
