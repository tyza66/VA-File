from fastapi import FastAPI
import uvicorn
import PyPDF2
import docx2txt


app = FastAPI(title="VAF-MIND1")

@app.get("/text", summary='text中判断', tags=['关键词判断'])
def text(path: str = None, key: str = None):
    end = "false"
    return end


@app.get("/docx", summary='doxc中判断', tags=['关键词判断'])
def docx(path: str = None, key: str = None):
    end = "false"
    return end

@app.get("/pdf", summary='pdf中判断', tags=['关键词判断'])
def pdf(path: str = None, key: str = None):
    end = "false"
    return end

if __name__ == '__main__':
    uvicorn.run("main:app", host='0.0.0.0', port=9092)