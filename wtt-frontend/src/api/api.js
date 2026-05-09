import axios from 'axios'

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  },
  withCredentials: true
})

apiClient.interceptors.response.use(
  response => response,
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default {
  adminLogin(username, password) {
    return apiClient.post('/api/adminlogin', { username, password })
  },
  playerLogin(username, password) {
    return apiClient.post('/api/playerlogin', null, { params: { username, password } })
  },
  getPlayerInfo(username) {
    return apiClient.get(`/player/username/${username}`)
  },
  getEquipment(playerName) {
    return apiClient.get(`/api/equipment/player/${playerName}`)
  },
  saveEquipment(equipment) {
    return apiClient.post('/api/equipment/save', equipment, {
      headers: { 'Content-Type': 'application/json' }
    })
  },
  getAdminMain() {
    return apiClient.get('/api/adminMain')
  },
  getAllPlayers() {
    return apiClient.get('/player/allPlayers')
  },
  addPlayer(player) {
    return apiClient.post('/player/add', null, { params: player })
  },
  deletePlayer(id) {
    return apiClient.delete(`/player/delete/${id}`)
  },
  updatePlayer(player) {
    return apiClient.put('/player/update', null, { params: player })
  },
  registerPlayer(player) {
    return apiClient.post('/player/register', null, { params: player })
  },
  getChatQuery(content, model) {
    return apiClient.post('/api/chat/query', null, { params: { content, model } })
  },
  analyzePlayer(playerId, model) {
    return apiClient.get(`/api/chat/analyze/${playerId}`, { params: { model } })
  },
  suggestStyle(playerId, style, model) {
    return apiClient.post(`/api/chat/suggest/${playerId}`, null, { params: { style, model } })
  },
  getAllLogs() {
    return apiClient.get('/api/log/all')
  },
  getLogsByUsername(username) {
    return apiClient.get(`/api/log/username/${username}`)
  },
  getLogsByModule(module) {
    return apiClient.get(`/api/log/module/${module}`)
  },
  getLogsByAction(actionType) {
    return apiClient.get(`/api/log/action/${actionType}`)
  },
  getLogCount() {
    return apiClient.get('/api/log/count')
  },
  deleteLog(id) {
    return apiClient.delete(`/api/log/${id}`)
  },
  getAllMatches() {
    return apiClient.get('/api/match/all')
  },
  getPlayerMatches(playerId) {
    return apiClient.get(`/api/match/player/${playerId}`)
  },
  addMatch(match) {
    return apiClient.post('/api/match/add', match, {
      headers: { 'Content-Type': 'application/json' }
    })
  },
  deleteMatch(id) {
    return apiClient.delete(`/api/match/${id}`)
  }
}