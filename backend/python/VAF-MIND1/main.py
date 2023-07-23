from fastapi import FastAPI
import uvicorn
import PyPDF2
import docx2txt


app = FastAPI(title="VAF-MIND1")

@app.get("/text", summary='text中判断', tags=['关键词判断'])
def text(path: str = None, key: str = None):
    end = "false"
    # 打开文件
    with open(path, encoding='utf-8') as file:
        # 读取文件内容1
        content = file.read()
        # 关闭文件
        file.close()
        # 打印文件内容
        if key in content:
            print(path)
            end = "true"
    return end


@app.get("/docx", summary='doxc中判断', tags=['关键词判断'])
def docx(path: str = None, key: str = None):
    end = "false"
    text = docx2txt.process(path)
    if key in text:
        end = "true"
    return end

@app.get("/pdf", summary='pdf中判断', tags=['关键词判断'])
def pdf(path: str = None, key: str = None):
    end = "false"
    with open(path, 'rb') as pdfFileObj:
        pdfReader = PyPDF2.PdfReader(pdfFileObj)
        num_pages = len(pdfReader.pages)
        count = 0
        text = ""
        while count < num_pages:
            pageObj = pdfReader.pages[count]
            count += 1
            text += pageObj.extract_text()
            if key in text:
                end = "true"
                break
        pdfFileObj.close()
    return end

if __name__ == '__main__':
    uvicorn.run("main:app", host='0.0.0.0', port=9092)