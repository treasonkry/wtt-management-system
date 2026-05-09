"""Chat API endpoints."""
from typing import Optional
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

from app.chains import PlayerAnalysisChain, PlayerSuggestChain, ChatQueryChain


router = APIRouter(prefix="/api/chat", tags=["chat"])


class PlayerAnalyzeRequest(BaseModel):
    """Request model for player analysis."""
    content: str
    model: Optional[str] = None


class SuggestRequest(BaseModel):
    """Request model for style suggestion."""
    content: str
    model: Optional[str] = None


class ChatQueryRequest(BaseModel):
    """Request model for free chat query."""
    content: str
    context: Optional[str] = None
    model: Optional[str] = None


@router.post("/analyze")
async def analyze_player(request: PlayerAnalyzeRequest):
    """Analyze player level and provide suggestions."""
    try:
        chain = PlayerAnalysisChain(model_name=request.model)
        result = chain.analyze_raw(request.content)
        return {"success": True, "data": result}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/suggest")
async def suggest_style(request: SuggestRequest):
    """Suggest playing style for player."""
    try:
        chain = PlayerSuggestChain(model_name=request.model)
        result = chain.suggest_raw(request.content)
        return {"success": True, "data": result}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/query")
async def chat_query(request: ChatQueryRequest):
    """Free-form chat query."""
    try:
        chain = ChatQueryChain(model_name=request.model)
        result = chain.query(
            question=request.content,
            context=request.context
        )
        return {"success": True, "data": result, "model": request.model or "default"}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get("/health")
async def health_check():
    """Health check endpoint."""
    return {"status": "ok", "service": "ai-service"}