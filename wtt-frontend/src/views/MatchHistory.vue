<template>
  <div class="match-history">
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
        <router-link to="/my-matches" class="nav-item active">
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
          <h1>我的比赛</h1>
          <p>查看您的比赛历史记录</p>
        </div>
      </header>

      <div class="stats-row animate-fade-in-up stagger-1">
        <div class="stat-card">
          <div class="stat-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4z"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ totalMatches }}</span>
            <span class="stat-label">总比赛数</span>
          </div>
        </div>
        <div class="stat-card win">
          <div class="stat-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M22 11.08V12a10 10 0 11-5.93-9.14"/>
              <polyline points="22,4 12,14.01 9,11.01"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ winCount }}</span>
            <span class="stat-label">获胜场次</span>
          </div>
        </div>
        <div class="stat-card lose">
          <div class="stat-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <line x1="15" y1="9" x2="9" y2="15"/>
              <line x1="9" y1="9" x2="15" y2="15"/>
            </svg>
          </div>
          <div class="stat-info">
            <span class="stat-value">{{ loseCount }}</span>
            <span class="stat-label">失利场次</span>
          </div>
        </div>
      </div>

      <div class="match-list animate-fade-in-up stagger-2">
        <div v-if="matches.length === 0" class="empty-state">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
            <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4zM5 4h4M5 4l2 7M19 4h-4M19 4l-2 7"/>
          </svg>
          <h3>暂无比赛记录</h3>
          <p>您还没有参加任何比赛</p>
        </div>

        <div v-else class="match-cards">
          <div v-for="(match, index) in matches" :key="match.id" :class="['match-card', `stagger-${Math.min(index + 1, 5)}`, { win: isWinner(match) }]">
            <div class="match-header">
              <div class="match-time">{{ formatTime(match.createdAt) }}</div>
              <div :class="['match-result', isWinner(match) ? 'result-win' : 'result-lose']">
                {{ isWinner(match) ? '胜' : '负' }}
              </div>
            </div>

            <div class="match-players">
              <div :class="['player', { winner: match.winnerId === match.player1Id }]">
                <div class="player-avatar">
                  {{ match.player1Name.charAt(0).toUpperCase() }}
                </div>
                <div class="player-info">
                  <span class="player-name">{{ match.player1Name }}</span>
                  <span class="player-role" v-if="match.player1Id === currentPlayerId">你</span>
                </div>
              </div>

              <div class="vs-section">
                <div class="large-score">{{ match.largeScore }}</div>
                <div class="vs-text">VS</div>
              </div>

              <div :class="['player', { winner: match.winnerId === match.player2Id }]">
                <div class="player-avatar">
                  {{ match.player2Name.charAt(0).toUpperCase() }}
                </div>
                <div class="player-info">
                  <span class="player-name">{{ match.player2Name }}</span>
                  <span class="player-role" v-if="match.player2Id === currentPlayerId">你</span>
                </div>
              </div>
            </div>

            <div class="match-details">
              <div class="detail-label">小比分</div>
              <div class="small-scores">
                <span v-for="(score, i) in parseSmallScores(match.smallScores)" :key="i" class="score-badge">
                  {{ score }}
                </span>
              </div>
            </div>

            <div class="match-footer">
              <div class="winner-info">
                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M8 21h8M12 17v4M7 4h10l-2 7h-6L7 4z"/>
                </svg>
                获胜者: {{ match.winnerName }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()
const matches = ref([])
const currentPlayerId = ref(null)

const totalMatches = computed(() => matches.value.length)
const winCount = computed(() => matches.value.filter(m => isWinner(m)).length)
const loseCount = computed(() => matches.value.filter(m => !isWinner(m)).length)

const isWinner = (match) => {
  return match.winnerId === currentPlayerId.value
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
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const fetchMatches = async () => {
  try {
    const response = await api.getPlayerMatches(currentPlayerId.value)
    if (response.data.success) {
      matches.value = response.data.data
    }
  } catch (error) {
    console.error('获取比赛记录失败:', error)
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
    currentPlayerId.value = user.id
    fetchMatches()
  }
})
</script>

<style scoped>
.match-history {
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

.nav-item.active {
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
  max-width: 900px;
  margin: 0 auto;
  padding: 48px 32px;
}

.page-header {
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

.stats-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
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
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
  border-radius: var(--radius-md);
}

.stat-card.win .stat-icon {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
}

.stat-card.lose .stat-icon {
  background: var(--danger-glow);
  color: var(--danger);
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-family: var(--font-display);
  font-size: 28px;
  font-weight: 700;
  color: var(--text-primary);
}

.stat-label {
  font-size: 13px;
  color: var(--text-muted);
}

.match-cards {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.match-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  padding: 24px;
  transition: all 0.3s;
}

.match-card:hover {
  border-color: var(--accent-primary);
  box-shadow: 0 4px 20px rgba(255, 107, 53, 0.1);
}

.match-card.win {
  border-left: 4px solid var(--accent-secondary);
}

.match-card:not(.win) {
  border-left: 4px solid var(--danger);
}

.match-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.match-time {
  font-size: 13px;
  color: var(--text-muted);
}

.match-result {
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.result-win {
  background: var(--accent-secondary-glow);
  color: var(--accent-secondary);
}

.result-lose {
  background: var(--danger-glow);
  color: var(--danger);
}

.match-players {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  margin-bottom: 20px;
}

.player {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.player.winner .player-avatar {
  background: linear-gradient(135deg, var(--accent-secondary), #00e6b8);
}

.player-avatar {
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  color: white;
  font-weight: 700;
  font-size: 18px;
  border-radius: var(--radius-md);
}

.player-info {
  display: flex;
  flex-direction: column;
}

.player-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-primary);
}

.player-role {
  font-size: 12px;
  color: var(--accent-primary);
  font-weight: 500;
}

.vs-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
}

.large-score {
  font-family: var(--font-display);
  font-size: 32px;
  font-weight: 800;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.vs-text {
  font-size: 12px;
  color: var(--text-muted);
  font-weight: 600;
}

.match-details {
  padding: 16px;
  background: var(--bg-secondary);
  border-radius: var(--radius-md);
  margin-bottom: 16px;
}

.detail-label {
  font-size: 12px;
  color: var(--text-muted);
  margin-bottom: 8px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.small-scores {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.score-badge {
  display: inline-block;
  padding: 4px 12px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 4px;
  font-size: 14px;
  font-family: var(--font-display);
  font-weight: 600;
}

.match-footer {
  display: flex;
  justify-content: flex-end;
}

.winner-info {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: var(--accent-secondary);
  font-weight: 500;
}

.empty-state {
  text-align: center;
  padding: 80px 20px;
  color: var(--text-muted);
}

.empty-state svg {
  margin-bottom: 20px;
  opacity: 0.4;
}

.empty-state h3 {
  font-size: 20px;
  margin-bottom: 8px;
  color: var(--text-secondary);
}

.empty-state p {
  font-size: 14px;
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
</style>
