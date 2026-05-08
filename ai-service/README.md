# WTT AI Service

LangChain-powered AI microservice for WTT management system.

## Supported Models

- **DeepSeek** - Cloud API (default)
- **OpenAI** - GPT-4/GPT-3.5
- **Ollama** - Local models (no internet required)

## Quick Start

### 1. Install dependencies

```bash
cd ai-service
pip install -r requirements.txt
```

### 2. Configure API keys

Edit `config/models.yaml`:

```yaml
default_model: deepseek

models:
  deepseek:
    provider: deepseek
    api_key: your-api-key-here
    model_name: deepseek-chat
```

### 3. Run the service

```bash
uvicorn app.main:app --reload --port 8000
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/chat/analyze | Analyze player |
| POST | /api/chat/suggest | Get style suggestions |
| POST | /api/chat/query | Free-form chat query |
| GET | /health | Health check |

## Local Models (Ollama)

To use local models without internet:

1. Install [Ollama](https://ollama.ai/)
2. Pull a model: `ollama pull llama3`
3. Update `config/models.yaml`:

```yaml
models:
  ollama:
    provider: ollama
    base_url: http://localhost:11434
    model_name: llama3
```

## Architecture

```
Java Spring Boot → AI Service (FastAPI) → DeepSeek/OpenAI/Ollama
     (port 8080)      (port 8000)
```

The AI service is called internally by Spring Boot. Frontend only talks to Java backend.