"""Chat API endpoints."""
from typing import Optional
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel

from app.chains import PlayerAnalysisChain, PlayerSuggestChain, ChatQueryChain


router = APIRouter(prefix="/api/chat", tags=["chat"])


class PlayerAnalyzeRequest(BaseModel):
    """Request model for player analysis."""
    player_id: int
    name: str
    points: int
    phone: Optional[str] = ""
    match_history: Optional[str] = ""
    equipment: Optional[str] = ""


class SuggestRequest(BaseModel):
    """Request model for style suggestion."""
    player_id: int
    name: str
    points: int
    analysis: Optional[str] = ""


class ChatQueryRequest(BaseModel):
    """Request model for free chat query."""
    content: str
    context: Optional[str] = None
    model: Optional[str] = None


@router.post("/analyze")
async def analyze_player(request: PlayerAnalyzeRequest):
    """Analyze player level and provide suggestions."""
    try:
        chain = PlayerAnalysisChain()
        result = chain.analyze(
            player_data={
                "name": request.name,
                "points": request.points,
                "phone": request.phone
            },
            match_history=request.match_history,
            equipment=request.equipment
        )
        return {"success": True, "data": result}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.post("/suggest")
async def suggest_style(request: SuggestRequest):
    """Suggest playing style for player."""
    try:
        chain = PlayerSuggestChain()
        result = chain.suggest(
            player_data={
                "name": request.name,
                "points": request.points
            },
            analysis=request.analysis
        )
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