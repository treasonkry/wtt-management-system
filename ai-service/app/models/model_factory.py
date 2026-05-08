"""Model factory - unified interface for multiple AI providers."""
from typing import Optional
from langchain_openai import ChatOpenAI
from langchain_ollama import ChatOllama
from langchain_core.messages import HumanMessage, SystemMessage, AIMessage

from app.config import get_model_config


class ChatModelFactory:
    """Factory for creating chat models from different providers."""
    
    @staticmethod
    def create_model(model_name: Optional[str] = None):
        """Create a chat model based on configuration."""
        config = get_model_config(model_name)
        
        if config.provider == "deepseek":
            return ChatOpenAI(
                model=config.model_name or "deepseek-chat",
                api_key=config.api_key,
                base_url="https://api.deepseek.com/v1",
                temperature=config.temperature,
                max_tokens=config.max_tokens
            )
        
        elif config.provider == "openai":
            return ChatOpenAI(
                model=config.model_name or "gpt-4",
                api_key=config.api_key,
                base_url=config.base_url or "https://api.openai.com/v1",
                temperature=config.temperature,
                max_tokens=config.max_tokens
            )
        
        elif config.provider == "ollama":
            return ChatOllama(
                model=config.model_name or "llama3",
                base_url=config.base_url or "http://localhost:11434",
                temperature=config.temperature
            )
        
        else:
            raise ValueError(f"Unknown provider: {config.provider}")


def create_chat_model(model_name: Optional[str] = None):
    """Convenience function to create chat model."""
    return ChatModelFactory.create_model(model_name)