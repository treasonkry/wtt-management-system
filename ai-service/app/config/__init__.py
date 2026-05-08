"""Configuration management for AI service."""
import os
from pathlib import Path
from typing import Optional
import yaml
from pydantic import BaseModel
from functools import lru_cache


BASE_DIR = Path(__file__).parent.parent
CONFIG_FILE = BASE_DIR / "config" / "models.yaml"
PROMPTS_DIR = BASE_DIR / "prompts"


class ModelConfig(BaseModel):
    provider: str
    api_key: Optional[str] = None
    base_url: Optional[str] = None
    model_name: Optional[str] = None
    temperature: float = 0.7
    max_tokens: int = 2000


class Config(BaseModel):
    default_model: str = "deepseek"
    models: dict[str, ModelConfig] = {}


@lru_cache()
def get_config() -> Config:
    """Load and cache configuration."""
    if not CONFIG_FILE.exists():
        return Config()
    
    with open(CONFIG_FILE, 'r', encoding='utf-8') as f:
        data = yaml.safe_load(f) or {}
    
    return Config(**data)


def load_prompt_template(name: str) -> str:
    """Load prompt template from file."""
    template_file = PROMPTS_DIR / f"{name}.yaml"
    if not template_file.exists():
        return ""
    
    with open(template_file, 'r', encoding='utf-8') as f:
        data = yaml.safe_load(f) or {}
    
    return data.get('template', '')


def get_model_config(model_name: str = None) -> ModelConfig:
    """Get model configuration by name."""
    config = get_config()
    name = model_name or config.default_model
    return config.models.get(name, ModelConfig(provider="openai"))