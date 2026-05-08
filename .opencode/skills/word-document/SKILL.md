---
name: word-document
description: |
  当用户需要处理Word文档（.docx）时触发。支持读取、编辑、创建Word文档，以及格式转换等通用办公文档处理场景。
  适用于以下场景：
  - 用户提到"Word文档"、"docx"、"文档处理"
  - 需要"读取"、"编辑"、"创建"、"修改"Word文档
  - 需要处理合同、报告、简历、表格等办公文档
  - 批量处理多个Word文档
  - Word与其他格式的转换
---

# Word文档处理技能

本技能使用 `python-docx` 库处理Word文档，支持读取、编辑、创建等功能。

## 环境要求

- Python 3.x
- 安装依赖：`pip install python-docx`

## 核心功能

### 1. 读取Word文档

读取文档内容，包括段落、表格、标题等：

```python
from docx import Document

def read_docx(file_path):
    doc = Document(file_path)
    content = []
    for para in doc.paragraphs:
        content.append({"type": "paragraph", "text": para.text, "style": para.style.name})
    for table in doc.tables:
        table_data = []
        for row in table.rows:
            row_data = [cell.text for cell in row.cells]
            table_data.append(row_data)
        content.append({"type": "table", "data": table_data})
    return content
```

**返回格式：**
- 段落： `{type: "paragraph", text: "文本内容", style: "样式名"}`
- 表格： `{type: "table", data: [["单元格1", "单元格2"], ...]}`

### 2. 编辑Word文档

#### 添加文本

```python
from docx import Document
from docx.shared import Pt, RGBColor
from docx.enum.text import WD_PARAGRAPH_ALIGNMENT

def add_text(file_path, text, style="Normal", bold=False):
    doc = Document(file_path)
    para = doc.add_paragraph(text, style=style)
    if bold:
        for run in para.runs:
            run.bold = True
    doc.save(file_path)
```

#### 修改文本

```python
def replace_text(file_path, old_text, new_text):
    doc = Document(file_path)
    for para in doc.paragraphs:
        if old_text in para.text:
            para.text = para.text.replace(old_text, new_text)
    for table in doc.tables:
        for row in table.rows:
            for cell in row.cells:
                if old_text in cell.text:
                    cell.text = cell.text.replace(old_text, new_text)
    doc.save(file_path)
```

#### 格式化文本

```python
def format_text(file_path, target_text, bold=True, italic=False, font_size=None, color=None):
    doc = Document(file_path)
    for para in doc.paragraphs:
        for run in para.runs:
            if target_text in run.text:
                run.bold = bold
                run.italic = italic
                if font_size:
                    run.font.size = Pt(font_size)
                if color:
                    run.font.color.rgb = RGBColor(*color)
    doc.save(file_path)
```

### 3. 创建新Word文档

```python
from docx import Document
from docx.shared import Inches, Pt
from docx.enum.text import WD_PARAGRAPH_ALIGNMENT

def create_docx(file_path, title=None, content=None):
    doc = Document()
    
    if title:
        heading = doc.add_heading(title, level=1)
        heading.alignment = WD_PARAGRAPH_ALIGNMENT.CENTER
    
    if content:
        for item in content:
            if item["type"] == "heading":
                doc.add_heading(item["text"], level=item.get("level", 1))
            elif item["type"] == "paragraph":
                doc.add_paragraph(item["text"])
            elif item["type"] == "table":
                table = doc.add_table(rows=len(item["data"]), cols=len(item["data"][0]))
                for i, row_data in enumerate(item["data"]):
                    row = table.rows[i]
                    for j, cell_text in enumerate(row_data):
                        row.cells[j].text = cell_text
    
    doc.save(file_path)
    return file_path
```

**content项类型：**
- `heading`: 标题 `{"type": "heading", "text": "标题文本", "level": 1}`
- `paragraph`: 段落 `{"type": "paragraph", "text": "段落文本"}`
- `table`: 表格 `{"type": "table", "data": [["A1", "A2"], ["B1", "B2"]]}`

### 4. 操作表格

```python
def add_table(file_path, data, headers=None):
    doc = Document(file_path)
    table = doc.add_table(rows=len(data), cols=len(data[0]))
    
    if headers:
        header_row = table.rows[0]
        for i, header in enumerate(headers):
            header_row.cells[i].text = header
    
    for i, row_data in enumerate(data):
        row = table.rows[i] if not headers else table.rows[i + 1]
        for j, cell_text in enumerate(row_data):
            row.cells[j].text = str(cell_text)
    
    doc.save(file_path)
```

### 5. 添加图片

```python
def add_image(file_path, image_path, width=None, height=None):
    doc = Document(file_path)
    picture = doc.add_picture(image_path)
    if width:
        picture.width = width
    if height:
        picture.height = height
    doc.save(file_path)
```

## 使用流程

1. **分析需求**：确定是读取、编辑还是创建
2. **编写脚本**：根据需求编写Python脚本
3. **执行脚本**：使用Bash工具运行脚本
4. **验证结果**：检查生成的文档

## 示例

**读取文档：**
用户说"帮我读取这份合同的内容" → 编写脚本读取并返回文本和表格

**编辑文档：**
用户说"把这份报告的标题加粗" → 编写脚本修改格式并保存

**创建文档：**
用户说"创建一个周报模板" → 编写脚本生成新的.docx文件