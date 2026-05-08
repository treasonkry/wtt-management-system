"""FastAPI main application - AI Service powered by LangChain."""
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.routers import chat_router


app = FastAPI(
    title="WTT AI Service",
    description="LangChain-powered AI service for WTT management system",
    version="1.0.0"
)

# CORS configuration - allow frontend requests
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Include routers
app.include_router(chat_router)


@app.get("/")
async def root():
    """Root endpoint."""
    return {
        "service": "WTT AI Service",
        "version": "1.0.0",
        "status": "running"
    }


@app.get("/health")
async def health():
    """Health check endpoint."""
    return {"status": "healthy"}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)