"""Player analysis chain - LangChain chain for player analysis."""
from typing import Optional, List
from langchain_core.messages import HumanMessage, SystemMessage
from langchain_core.prompts import ChatPromptTemplate, HumanMessagePromptTemplate, SystemMessagePromptTemplate

from app.models import create_chat_model
from app.config import load_prompt_template


class PlayerAnalysisChain:
    """Chain for analyzing player level and providing suggestions."""
    
    def __init__(self, model_name: Optional[str] = None):
        self.model = create_chat_model(model_name)
        self.prompt_template = self._build_prompt()
    
    def _build_prompt(self):
        """Build the prompt template from config."""
        system_template = load_prompt_template("analyze_system")
        human_template = load_prompt_template("analyze_human")
        
        if not system_template:
            system_template = """你是一位专业的乒乓球教练，拥有丰富的球员训练和比赛分析经验。
请根据球员的详细信息给出专业、客观的分析和建议。"""
        
        if not human_template:
            human_template = """球员信息：
姓名：{name}
积分：{points}
手机号：{phone}
比赛记录：{match_history}
器材配置：{equipment}

请分析这位球员的水平，并给出具体的提升建议。"""
        
        return ChatPromptTemplate.from_messages([
            SystemMessagePromptTemplate.from_template(system_template),
            HumanMessagePromptTemplate.from_template(human_template)
        ])
    
    def analyze(self, player_data: dict, match_history: str = "", equipment: str = "") -> str:
        """Analyze player and return analysis result."""
        input_data = {
            "name": player_data.get("name", "未知"),
            "points": player_data.get("points", 0),
            "phone": player_data.get("phone", "未设置"),
            "match_history": match_history or "暂无比赛记录",
            "equipment": equipment or "未配置器材"
        }

        chain = self.prompt_template | self.model
        result = chain.invoke(input_data)
        return result.content if hasattr(result, 'content') else str(result)

    def analyze_raw(self, content: str) -> str:
        """Analyze with raw content string."""
        messages = [
            SystemMessage(content="你是一位专业的乒乓球教练，拥有丰富的球员训练和比赛分析经验。请根据提供的信息给出专业、客观的分析和建议。"),
            HumanMessage(content=content)
        ]
        result = self.model.invoke(messages)
        return result.content if hasattr(result, 'content') else str(result)


class PlayerSuggestChain:
    """Chain for suggesting playing style."""
    
    def __init__(self, model_name: Optional[str] = None):
        self.model = create_chat_model(model_name)
        self.prompt_template = self._build_prompt()
    
    def _build_prompt(self):
        """Build the prompt template."""
        system_template = load_prompt_template("suggest_system")
        human_template = load_prompt_template("suggest_human")
        
        if not system_template:
            system_template = """你是一位经验丰富的乒乓球战术教练，精通各种打法和战术风格。
请根据球员的特点，给出针对性的打法建议。"""
        
        if not human_template:
            human_template = """球员信息：
姓名：{name}
积分：{points}
比赛风格分析：{analysis}

请给出这位球员适合的打法风格和训练重点。"""
        
        return ChatPromptTemplate.from_messages([
            SystemMessagePromptTemplate.from_template(system_template),
            HumanMessagePromptTemplate.from_template(human_template)
        ])
    
    def suggest(self, player_data: dict, analysis: str = "") -> str:
        """Generate playing style suggestions."""
        input_data = {
            "name": player_data.get("name", "未知"),
            "points": player_data.get("points", 0),
            "analysis": analysis or "暂无分析数据"
        }

        chain = self.prompt_template | self.model
        result = chain.invoke(input_data)
        return result.content if hasattr(result, 'content') else str(result)

    def suggest_raw(self, content: str) -> str:
        """Suggest with raw content string."""
        messages = [
            SystemMessage(content="你是一位经验丰富的乒乓球战术教练，精通各种打法和战术风格。请根据提供的信息给出针对性的打法建议。"),
            HumanMessage(content=content)
        ]
        result = self.model.invoke(messages)
        return result.content if hasattr(result, 'content') else str(result)


class ChatQueryChain:
    """Chain for free-form chat queries."""
    
    def __init__(self, model_name: Optional[str] = None):
        self.model = create_chat_model(model_name)
    
    def query(self, question: str, context: Optional[str] = None) -> str:
        """Process a free-form query."""
        if context:
            prompt = f"背景信息：{context}\n\n问题：{question}"
        else:
            prompt = question
        
        messages = [
            SystemMessage(content="你是一位专业的乒乓球教练，请回答关于乒乓球训练、比赛、技术等方面的问题。"),
            HumanMessage(content=prompt)
        ]
        
        result = self.model.invoke(messages)
        return result.content if hasattr(result, 'content') else str(result)