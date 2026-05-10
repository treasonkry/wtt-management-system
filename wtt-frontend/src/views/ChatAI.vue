<template>
  <div class="chat-ai">
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
        <router-link :to="isAdmin ? '/admin' : '/player'" class="nav-item">
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
          <h1>AI 智能分析</h1>
          <p>基于球员数据提供专业的乒乓球水平分析</p>
        </div>
      </header>

      <div class="chat-container animate-fade-in-up stagger-1">
        <div class="player-select" v-if="!selectedPlayer">
          <div class="select-header">
            <h2>选择球员</h2>
            <p>选择一个球员开始分析</p>
          </div>
          <div class="player-grid">
            <div
              v-for="player in players"
              :key="player.id"
              class="player-card"
              @click="selectPlayer(player)"
            >
              <div class="player-avatar">
                {{ player.name.charAt(0).toUpperCase() }}
              </div>
              <div class="player-info">
                <h3>{{ player.name }}</h3>
                <p>
                  <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <polygon points="12,2 15.09,8.26 22,9.27 17,14.14 18.18,21.02 12,17.77 5.82,21.02 7,14.14 2,9.27 8.91,8.26"/>
                  </svg>
                  {{ player.points }} 积分
                </p>
              </div>
              <svg class="arrow" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="9,18 15,12 9,6"/>
              </svg>
            </div>
          </div>
        </div>

        <div class="chat-area" v-else>
          <div class="chat-header">
            <div class="selected-player">
              <div class="player-avatar">
                {{ selectedPlayer.name.charAt(0).toUpperCase() }}
              </div>
              <div>
                <h3>{{ selectedPlayer.name }}</h3>
                <p>{{ selectedPlayer.points }} 积分</p>
              </div>
            </div>
            <button class="back-btn" @click="selectedPlayer = null">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <polyline points="15,18 9,12 15,6"/>
              </svg>
              返回
            </button>
          </div>

          <div class="tabs">
            <button :class="{ active: tab === 'analyze' }" @click="tab = 'analyze'">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
              </svg>
              水平分析
            </button>
            <button :class="{ active: tab === 'suggest' }" @click="tab = 'suggest'">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <path d="M9.09 9a3 3 0 015.83 1c0 2-3 3-3 3"/>
                <line x1="12" y1="17" x2="12.01" y2="17"/>
              </svg>
              打法建议
            </button>
            <button :class="{ active: tab === 'chat' }" @click="tab = 'chat'">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
              </svg>
              自由问答
            </button>
          </div>

          <div class="model-select-container">
            <select v-model="selectedModel" class="model-select">
              <option v-for="opt in modelOptions" :key="opt.key" :value="opt.key">
                {{ opt.label }}
              </option>
            </select>
          </div>

          <div class="chat-messages">
            <Transition name="fade" mode="out-in">
              <div v-if="aiMessage" class="message ai-message">
                <div class="message-icon">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M12 2a10 10 0 1010 10H12V2z"/>
                    <path d="M12 2a10 10 0 00-3.5 19.3"/>
                  </svg>
                </div>
                <div class="message-content" v-html="formatMessage(aiMessage)"></div>
              </div>
              <div v-else-if="loading" class="message loading">
                <div class="loading-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
                <p>AI 分析中...</p>
              </div>
              <div v-else class="message empty">
                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                  <path d="M21 15a2 2 0 01-2 2H7l-4 4V5a2 2 0 012-2h14a2 2 0 012 2z"/>
                </svg>
                <p>点击下方按钮获取 AI 分析</p>
              </div>
            </Transition>
          </div>

          <div class="chat-input" v-if="tab === 'chat'">
            <input
              v-model="chatInput"
              placeholder="输入您的问题..."
              @keyup.enter="sendChat"
              :disabled="loading"
            />
            <button @click="sendChat" :disabled="loading || !chatInput">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="22" y1="2" x2="11" y2="13"/>
                <polygon points="22,2 15,22 11,13 2,9"/>
              </svg>
            </button>
          </div>
          <div class="action-buttons" v-else>
            <button @click="getAnalysis" :disabled="loading" class="analyze-btn">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
              </svg>
              {{ tab === 'analyze' ? '开始分析' : '获取建议' }}
            </button>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()
const players = ref([])
const selectedPlayer = ref(null)
const tab = ref('analyze')
const chatInput = ref('')
const aiMessage = ref('')
const loading = ref(false)
const isAdmin = ref(false)
const selectedModel = ref('deepseek')
const modelOptions = ref([])

onMounted(async () => {
  const userStr = sessionStorage.getItem('user')
  if (userStr) {
    const user = JSON.parse(userStr)
    isAdmin.value = user.type === 'admin'
  }
  try {
    const [playersRes, modelsRes] = await Promise.all([
      api.getAllPlayers(),
      api.getModelOptions()
    ])
    if (playersRes.data.success) {
      players.value = playersRes.data.data
    }
    if (modelsRes.data.success) {
      modelOptions.value = modelsRes.data.data
      selectedModel.value = modelsRes.data.default || 'deepseek'
    }
    const savedPlayer = sessionStorage.getItem('analyzePlayer')
    if (savedPlayer) {
      selectedPlayer.value = JSON.parse(savedPlayer)
      sessionStorage.removeItem('analyzePlayer')
    }
  } catch (error) {
    console.error('获取数据失败:', error)
  }
})

const selectPlayer = (player) => {
  selectedPlayer.value = player
  aiMessage.value = ''
}

const getAnalysis = async () => {
  if (!selectedPlayer.value) return
  loading.value = true
  aiMessage.value = ''
  try {
    let response
    if (tab.value === 'analyze') {
      response = await api.analyzePlayer(selectedPlayer.value.id, selectedModel.value)
    } else {
      response = await api.suggestStyle(selectedPlayer.value.id, null, selectedModel.value)
    }
    if (response.data.success) {
      aiMessage.value = response.data.data
    } else {
      aiMessage.value = '获取分析失败: ' + response.data.message
    }
  } catch (error) {
    aiMessage.value = '请求失败，请检查AI服务是否可用'
  } finally {
    loading.value = false
  }
}

const sendChat = async () => {
  if (!chatInput.value || loading.value) return
  const question = chatInput.value
  chatInput.value = ''
  loading.value = true
  aiMessage.value = ''
  try {
    const response = await api.getChatQuery(question, selectedModel.value)
    if (response.data.success) {
      aiMessage.value = response.data.data
    } else {
      aiMessage.value = '问答失败: ' + response.data.message
    }
  } catch (error) {
    aiMessage.value = '请求失败，请检查网络和AI服务'
  } finally {
    loading.value = false
  }
}

const formatMessage = (msg) => {
  return msg.replace(/\n/g, '<br>')
}

const logout = () => {
  sessionStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.chat-ai {
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
  max-width: 1000px;
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

.chat-container {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-xl);
  overflow: hidden;
  min-height: 600px;
  display: flex;
  flex-direction: column;
}

.player-select {
  padding: 40px;
  flex: 1;
}

.select-header {
  text-align: center;
  margin-bottom: 32px;
}

.select-header h2 {
  font-size: 22px;
  margin-bottom: 8px;
}

.select-header p {
  color: var(--text-muted);
  font-size: 14px;
}

.player-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 16px;
}

.player-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all 0.25s;
}

.player-card:hover {
  border-color: var(--accent-primary);
  transform: translateX(4px);
  box-shadow: var(--shadow-glow-primary);
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
  flex-shrink: 0;
}

.player-info {
  flex: 1;
}

.player-info h3 {
  font-size: 16px;
  margin-bottom: 4px;
}

.player-info p {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: var(--text-muted);
}

.player-info p svg {
  color: var(--accent-primary);
}

.arrow {
  color: var(--text-muted);
  transition: all 0.2s;
}

.player-card:hover .arrow {
  color: var(--accent-primary);
  transform: translateX(4px);
}

.chat-area {
  padding: 32px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 28px;
  padding-bottom: 20px;
  border-bottom: 1px solid var(--border);
}

.selected-player {
  display: flex;
  align-items: center;
  gap: 16px;
}

.selected-player .player-avatar {
  width: 44px;
  height: 44px;
  font-size: 16px;
}

.selected-player h3 {
  font-size: 18px;
  margin-bottom: 2px;
}

.selected-player p {
  font-size: 13px;
  color: var(--text-muted);
}

.back-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 10px 16px;
  background: var(--bg-secondary);
  color: var(--text-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  font-size: 14px;
}

.back-btn:hover {
  background: var(--bg-elevated);
  color: var(--text-primary);
}

.tabs {
  display: flex;
  gap: 8px;
  margin-bottom: 24px;
}

.tabs button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background: var(--bg-secondary);
  color: var(--text-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
}

.tabs button:hover {
  color: var(--text-primary);
  border-color: var(--border-hover);
}

.tabs button.active {
  background: var(--accent-primary);
  color: white;
  border-color: var(--accent-primary);
}

.model-select-container {
  margin-bottom: 20px;
}

.model-select {
  padding: 10px 16px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-primary);
  font-size: 14px;
  cursor: pointer;
}

.model-select:focus {
  outline: none;
  border-color: var(--accent-primary);
}

.chat-messages {
  flex: 1;
  min-height: 300px;
  padding: 24px;
  background: var(--bg-secondary);
  border-radius: var(--radius-lg);
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.message {
  width: 100%;
}

.message.ai-message {
  display: flex;
  gap: 16px;
  padding: 20px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
}

.message-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--accent-primary-glow);
  color: var(--accent-primary);
  border-radius: var(--radius-sm);
  flex-shrink: 0;
}

.message-content {
  flex: 1;
  line-height: 1.7;
  white-space: pre-wrap;
  color: var(--text-primary);
}

.message.loading {
  text-align: center;
}

.loading-dots {
  display: flex;
  justify-content: center;
  gap: 6px;
  margin-bottom: 12px;
}

.loading-dots span {
  width: 10px;
  height: 10px;
  background: var(--accent-primary);
  border-radius: 50%;
  animation: bounce 1.4s ease-in-out infinite both;
}

.loading-dots span:nth-child(1) { animation-delay: -0.32s; }
.loading-dots span:nth-child(2) { animation-delay: -0.16s; }

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}

.message.loading p {
  color: var(--text-muted);
  font-size: 14px;
}

.message.empty {
  text-align: center;
  color: var(--text-muted);
}

.message.empty svg {
  opacity: 0.3;
  margin-bottom: 12px;
}

.message.empty p {
  font-size: 14px;
}

.chat-input {
  display: flex;
  gap: 12px;
}

.chat-input input {
  flex: 1;
  padding: 16px 20px;
  background: var(--bg-secondary);
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  color: var(--text-primary);
  font-size: 15px;
}

.chat-input input:focus {
  outline: none;
  border-color: var(--accent-primary);
}

.chat-input input::placeholder {
  color: var(--text-muted);
}

.chat-input button {
  width: 52px;
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--accent-primary), #ff8555);
  color: white;
  border: none;
  border-radius: var(--radius-md);
}

.chat-input button:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-primary);
}

.chat-input button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.action-buttons {
  display: flex;
  justify-content: center;
}

.analyze-btn {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 16px 32px;
  background: linear-gradient(135deg, var(--accent-primary), #ff8555);
  color: white;
  border: none;
  border-radius: var(--radius-md);
  font-size: 15px;
  font-weight: 600;
}

.analyze-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-primary);
}

.analyze-btn:disabled {
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
</style>
