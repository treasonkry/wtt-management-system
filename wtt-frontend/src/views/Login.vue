<template>
  <div class="login-container">
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
      <div class="grid-pattern"></div>
    </div>

    <div class="login-card animate-scale-in">
      <div class="brand">
        <div class="logo">
          <svg width="48" height="48" viewBox="0 0 48 48" fill="none">
            <circle cx="24" cy="24" r="22" stroke="url(#logoGradient)" stroke-width="3"/>
            <path d="M16 24C16 19.5817 19.5817 16 24 16V16C28.4183 16 32 19.5817 32 24V32C32 36.4183 28.4183 40 24 40V40C19.5817 40 16 36.4183 16 32V24Z" fill="url(#logoGradient)"/>
            <circle cx="24" cy="24" r="6" fill="var(--bg-card)"/>
            <defs>
              <linearGradient id="logoGradient" x1="0" y1="0" x2="48" y2="48">
                <stop stop-color="#ff6b35"/>
                <stop offset="1" stop-color="#00d4aa"/>
              </linearGradient>
            </defs>
          </svg>
        </div>
        <h1>WTT</h1>
        <p class="tagline">World Table Tennis Management</p>
      </div>

      <div class="login-tabs">
        <button
          :class="{ active: loginType === 'admin' }"
          @click="loginType = 'admin'"
        >
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 4a4 4 0 100 8 4 4 0 000-8zM6 20v-2a6 6 0 0112 0v2"/>
          </svg>
          管理员
        </button>
        <button
          :class="{ active: loginType === 'player' }"
          @click="loginType = 'player'"
        >
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="8" r="5"/>
            <path d="M3 21v-2a7 7 0 0114 0v2"/>
          </svg>
          球员
        </button>
      </div>

      <form @submit.prevent="handleLogin" class="login-form">
        <Transition name="fade" mode="out-in">
          <div v-if="loginType === 'admin'" key="admin" class="form-fields">
            <div class="input-group">
              <label>用户名</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"/>
                  <circle cx="12" cy="7" r="4"/>
                </svg>
                <input
                  v-model="adminForm.username"
                  type="text"
                  placeholder="输入用户名"
                  required
                />
              </div>
            </div>
            <div class="input-group">
              <label>密码</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2"/>
                  <path d="M7 11V7a5 5 0 0110 0v4"/>
                </svg>
                <input
                  v-model="adminForm.password"
                  type="password"
                  placeholder="输入密码"
                  required
                />
              </div>
            </div>
          </div>
          <div v-else key="player" class="form-fields">
            <div class="input-group">
              <label>用户名</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"/>
                  <circle cx="12" cy="7" r="4"/>
                </svg>
                <input
                  v-model="playerForm.username"
                  type="text"
                  placeholder="输入用户名"
                  required
                />
              </div>
            </div>
            <div class="input-group">
              <label>密码</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2"/>
                  <path d="M7 11V7a5 5 0 0110 0v4"/>
                </svg>
                <input
                  v-model="playerForm.password"
                  type="password"
                  placeholder="输入密码"
                  required
                />
              </div>
            </div>
          </div>
        </Transition>

        <button type="submit" class="login-btn" :disabled="loading">
          <span v-if="loading" class="loading-spinner"></span>
          <span v-else>{{ loginType === 'admin' ? '进入后台' : '进入系统' }}</span>
        </button>
      </form>

      <Transition name="fade">
        <div v-if="message" :class="['message', messageType]">
          {{ message }}
        </div>
      </Transition>

      <div class="register-section" v-if="loginType === 'player'">
        <p>还没有账号？</p>
        <button class="register-btn" @click="showRegisterModal = true">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M16 21v-2a4 4 0 00-4-4H6a4 4 0 00-4 4v2"/>
            <circle cx="9" cy="7" r="4"/>
            <line x1="19" y1="8" x2="19" y2="14"/>
            <line x1="22" y1="11" x2="16" y2="11"/>
          </svg>
          立即注册
        </button>
      </div>
    </div>

    <Transition name="modal">
      <div v-if="showRegisterModal" class="modal-overlay" @click.self="showRegisterModal = false">
        <div class="modal-card animate-scale-in">
          <div class="modal-header">
            <h2>球员注册</h2>
            <button class="close-btn" @click="showRegisterModal = false">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <line x1="18" y1="6" x2="6" y2="18"/>
                <line x1="6" y1="6" x2="18" y2="18"/>
              </svg>
            </button>
          </div>

          <form @submit.prevent="handleRegister" class="register-form">
            <div class="input-group">
              <label>姓名</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="8" r="5"/>
                  <path d="M3 21v-2a7 7 0 0114 0v2"/>
                </svg>
                <input v-model="registerForm.name" type="text" placeholder="真实姓名" required />
              </div>
            </div>
            <div class="input-group">
              <label>用户名</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 00-4-4H8a4 4 0 00-4 4v2"/>
                  <circle cx="12" cy="7" r="4"/>
                </svg>
                <input v-model="registerForm.username" type="text" placeholder="登录用户名" required />
              </div>
            </div>
            <div class="input-group">
              <label>密码</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="11" width="18" height="11" rx="2"/>
                  <path d="M7 11V7a5 5 0 0110 0v4"/>
                </svg>
                <input v-model="registerForm.password" type="password" placeholder="至少6位" required minlength="6" />
              </div>
            </div>
            <div class="input-group">
              <label>手机号</label>
              <div class="input-wrapper">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="5" y="2" width="14" height="20" rx="2"/>
                  <line x1="12" y1="18" x2="12" y2="18"/>
                </svg>
                <input v-model="registerForm.phone" type="text" placeholder="11位手机号" required pattern="[0-9]{11}" />
              </div>
            </div>

            <div class="modal-actions">
              <button type="button" class="btn-cancel" @click="showRegisterModal = false">取消</button>
              <button type="submit" class="btn-submit" :disabled="registerLoading">
                <span v-if="registerLoading" class="loading-spinner"></span>
                <span v-else>注册</span>
              </button>
            </div>

            <Transition name="fade">
              <div v-if="registerMessage" :class="['message', registerMessageType]">
                {{ registerMessage }}
              </div>
            </Transition>
          </form>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api/api'

const router = useRouter()
const loginType = ref('admin')
const loading = ref(false)
const message = ref('')
const messageType = ref('')

const adminForm = ref({ username: '', password: '' })
const playerForm = ref({ username: '', password: '' })

const showRegisterModal = ref(false)
const registerLoading = ref(false)
const registerMessage = ref('')
const registerMessageType = ref('')
const registerForm = ref({ name: '', username: '', password: '', phone: '' })

const handleLogin = async () => {
  loading.value = true
  message.value = ''
  try {
    if (loginType.value === 'admin') {
      const response = await api.adminLogin(adminForm.value.username, adminForm.value.password)
      if (response.data.message === '登录成功') {
        sessionStorage.setItem('user', JSON.stringify({ type: 'admin', username: adminForm.value.username }))
        router.push('/admin')
      } else {
        message.value = response.data.message
        messageType.value = 'error'
      }
    } else {
      const response = await api.playerLogin(playerForm.value.username, playerForm.value.password)
      if (response.data.message === '登录成功') {
        const playerInfoRes = await api.getPlayerInfo(playerForm.value.username)
        if (playerInfoRes.data.success) {
          const playerData = playerInfoRes.data.data
          sessionStorage.setItem('user', JSON.stringify({
            type: 'player',
            username: playerForm.value.username,
            id: playerData.id,
            name: playerData.name
          }))
        } else {
          sessionStorage.setItem('user', JSON.stringify({ type: 'player', username: playerForm.value.username }))
        }
        router.push('/player')
      } else {
        message.value = response.data.message
        messageType.value = 'error'
      }
    }
  } catch (error) {
    message.value = '登录失败，请检查网络或服务器'
    messageType.value = 'error'
  } finally {
    loading.value = false
  }
}

const handleRegister = async () => {
  registerLoading.value = true
  registerMessage.value = ''
  try {
    if (registerForm.value.password.length < 6) {
      registerMessage.value = '密码长度不能少于6位'
      registerMessageType.value = 'error'
      return
    }
    const response = await api.registerPlayer(registerForm.value)
    if (response.data.success) {
      registerMessage.value = '注册成功，请登录'
      registerMessageType.value = 'success'
      setTimeout(() => {
        showRegisterModal.value = false
        registerForm.value = { name: '', username: '', password: '', phone: '' }
        registerMessage.value = ''
      }, 1500)
    } else {
      registerMessage.value = response.data.message || '注册失败'
      registerMessageType.value = 'error'
    }
  } catch (error) {
    registerMessage.value = '注册失败，请检查网络或服务器'
    registerMessageType.value = 'error'
  } finally {
    registerLoading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  overflow: hidden;
}

.bg-decoration {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.4;
}

.circle-1 {
  width: 500px;
  height: 500px;
  background: var(--accent-primary);
  top: -150px;
  right: -100px;
  animation: pulse 8s ease-in-out infinite;
}

.circle-2 {
  width: 400px;
  height: 400px;
  background: var(--accent-secondary);
  bottom: -100px;
  left: -100px;
  animation: pulse 10s ease-in-out infinite;
  animation-delay: 2s;
}

.grid-pattern {
  position: absolute;
  inset: 0;
  background-image:
    linear-gradient(rgba(255,255,255,0.02) 1px, transparent 1px),
    linear-gradient(90deg, rgba(255,255,255,0.02) 1px, transparent 1px);
  background-size: 60px 60px;
}

.login-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-xl);
  padding: 48px;
  width: 100%;
  max-width: 440px;
  box-shadow: var(--shadow-elevated);
  position: relative;
}

.brand {
  text-align: center;
  margin-bottom: 40px;
}

.logo {
  margin-bottom: 16px;
  animation: fadeInUp 0.6s ease forwards;
}

.brand h1 {
  font-size: 36px;
  font-weight: 800;
  letter-spacing: 4px;
  background: linear-gradient(135deg, var(--accent-primary), var(--accent-secondary));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 8px;
}

.tagline {
  font-size: 13px;
  color: var(--text-muted);
  letter-spacing: 1px;
}

.login-tabs {
  display: flex;
  gap: 8px;
  background: var(--bg-secondary);
  padding: 6px;
  border-radius: var(--radius-md);
  margin-bottom: 32px;
}

.login-tabs button {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 12px 16px;
  background: transparent;
  color: var(--text-secondary);
  border-radius: var(--radius-sm);
  font-size: 14px;
  font-weight: 500;
}

.login-tabs button:hover {
  color: var(--text-primary);
}

.login-tabs button.active {
  background: var(--bg-elevated);
  color: var(--text-primary);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.login-tabs button svg {
  opacity: 0.7;
}

.login-tabs button.active svg {
  opacity: 1;
  color: var(--accent-primary);
}

.form-fields {
  margin-bottom: 24px;
}

.input-group {
  margin-bottom: 20px;
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

.login-btn {
  width: 100%;
  padding: 16px;
  background: linear-gradient(135deg, var(--accent-primary), #ff8555);
  color: white;
  border: none;
  border-radius: var(--radius-md);
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 0.5px;
  cursor: pointer;
  transition: all 0.25s;
  position: relative;
  overflow: hidden;
}

.login-btn::before {
  content: '';
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, transparent, rgba(255,255,255,0.15));
  opacity: 0;
  transition: opacity 0.25s;
}

.login-btn:hover::before {
  opacity: 1;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-primary);
}

.login-btn:active {
  transform: translateY(0);
}

.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
  transform: none;
}

.loading-spinner {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 2px solid rgba(255,255,255,0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.message {
  margin-top: 20px;
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

.register-section {
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid var(--border);
  text-align: center;
}

.register-section p {
  font-size: 14px;
  color: var(--text-muted);
  margin-bottom: 12px;
}

.register-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  background: transparent;
  color: var(--accent-secondary);
  border: 1px solid var(--accent-secondary);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 500;
}

.register-btn:hover {
  background: var(--accent-secondary-glow);
  transform: translateY(-2px);
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
  max-width: 420px;
  box-shadow: var(--shadow-elevated);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.modal-header h2 {
  font-size: 24px;
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

.register-form .input-group {
  margin-bottom: 18px;
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
  background: linear-gradient(135deg, var(--accent-secondary), #00e6b8);
  color: var(--bg-primary);
  border-radius: var(--radius-md);
  font-size: 14px;
  font-weight: 600;
}

.btn-submit:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow-glow-secondary);
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
