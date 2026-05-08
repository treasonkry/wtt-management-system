from openpyxl import Workbook
import sys
import json

def create_excel(file_path, data_json=None, headers=None):
    wb = Workbook()
    ws = wb.active
    ws.title = "Sheet1"

    if headers:
        ws.append(headers)

    if data_json:
        data = json.loads(data_json) if isinstance(data_json, str) else data_json
        for row in data:
            ws.append(row)

    wb.save(file_path)
    return file_path

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python create_excel.py <file_path> [data_json]")
        sys.exit(1)

    file_path = sys.argv[1]
    data_json = sys.argv[2] if len(sys.argv) > 2 else None

    create_excel(file_path, data_json)
    print(f"Created: {file_path}")