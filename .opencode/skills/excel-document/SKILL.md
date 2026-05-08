---
name: excel-document
description: |
  当用户需要处理Excel文件（.xlsx/.xls）时触发。支持读取、编辑、创建Excel文件及数据处理。
  适用于以下场景：
  - 用户提到"Excel"、"xlsx"、"电子表格"
  - 需要"读取"、"编辑"、"创建"、"修改"Excel内容
  - 财务报表、数据统计、表格处理
  - 批量处理多个Excel文件
  - 数据分析和格式调整
---

# Excel文档处理技能

本技能使用 `openpyxl` 库处理Excel文件，支持读取、编辑、创建等功能。

## 环境要求

- Python 3.x
- 安装依赖：`pip install openpyxl`

## 核心功能

### 1. 读取Excel文件

读取文档内容，支持单元格数据和格式：

```python
from openpyxl import load_workbook

def read_excel(file_path, sheet_name=None):
    wb = load_workbook(file_path)
    ws = wb[sheet_name] if sheet_name else wb.active

    data = []
    for row in ws.iter_rows(values_only=True):
        data.append(list(row))

    return {
        "sheet_name": ws.title,
        "data": data
    }
```

**返回格式：**
```json
{
  "sheet_name": "Sheet1",
  "data": [["姓名", "年龄"], ["张三", 25], ["李四", 30]]
}
```

### 2. 读取指定范围

```python
def read_range(file_path, min_row=1, max_row=10, min_col=1, max_col=5):
    wb = load_workbook(file_path)
    ws = wb.active

    data = []
    for row in ws.iter_rows(min_row=min_row, max_row=max_row,
                           min_col=min_col, max_col=max_col,
                           values_only=True):
        data.append(list(row))

    return data
```

### 3. 编辑Excel文件

#### 修改单元格

```python
def set_cell(file_path, cell_ref, value):
    wb = load_workbook(file_path)
    ws = wb.active
    ws[cell_ref] = value
    wb.save(file_path)
```

#### 批量修改

```python
def update_cells(file_path, updates):
    wb = load_workbook(file_path)
    ws = wb.active

    for cell_ref, value in updates.items():
        ws[cell_ref] = value

    wb.save(file_path)
```

### 4. 添加数据

```python
def append_row(file_path, data):
    wb = load_workbook(file_path)
    ws = wb.active
    ws.append(data)
    wb.save(file_path)
```

### 5. 创建新Excel文件

```python
from openpyxl import Workbook

def create_excel(file_path, data=None, headers=None):
    wb = Workbook()
    ws = wb.active
    ws.title = "Sheet1"

    if headers:
        ws.append(headers)

    if data:
        for row in data:
            ws.append(row)

    wb.save(file_path)
    return file_path
```

**data格式：** `[["A1", "B1"], ["A2", "B2"]]`

### 6. 格式化

```python
from openpyxl.styles import Font, PatternFill, Alignment

def format_header(file_path, row=1):
    wb = load_workbook(file_path)
    ws = wb.active

    header_fill = PatternFill(start_color="CCE5FF", end_color="CCE5FF", fill_type="solid")
    header_font = Font(bold=True)

    for cell in ws[row]:
        cell.fill = header_fill
        cell.font = header_font
        cell.alignment = Alignment(horizontal="center")

    wb.save(file_path)
```

### 7. Excel公式

```python
def add_formula(file_path, cell_ref, formula):
    wb = load_workbook(file_path)
    ws = wb.active
    ws[cell_ref] = formula
    wb.save(file_path)
```

示例：`"=SUM(A1:A10)"`、`"=AVERAGE(B1:B5)"`

## 使用流程

1. **分析需求**：确定是读取、编辑还是创建
2. **编写脚本**：根据需求编写Python脚本
3. **执行脚本**：使用Bash工具运行脚本
4. **验证结果**：检查生成的文件

## 示例

**读取文档：**
用户说"帮我看看这个销售报表的数据" → 编写脚本读取并返回数据

**编辑文档：**
用户说"把第三行第二列的值改成100" → 编写脚本修改单元格并保存

**创建文档：**
用户说"创建一个学生成绩表" → 编写脚本生成新的.xlsx文件