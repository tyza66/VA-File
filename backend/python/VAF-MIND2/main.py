from fastapi import FastAPI
import uvicorn
from pdf2docx import Converter
from docx2pdf import convert
import pytesseract
from PIL import Image

pytesseract.pytesseract.tesseract_cmd = r'D:\Software\Tesseract-OCR\tesseract.exe'


app = FastAPI(title="VAF-MIND2")

#pdf转docx
@app.get("/pdf2docx", summary='pdf转docx', tags=['pdf转docx'])
def pdf2docx(path: str = None):
    end = "false"
    #调库进行转换
    cv = Converter(path)
    cv.convert(path[:-4] + ".docx")
    cv.close()
    end = "true"
    return end

#docx转pdf
@app.get("/docx2pdf", summary='docx转pdf', tags=['docx转pdf'])
def docx2pdf(path: str = None):
    end = "false"
    #调库进行转换
    convert(path, path[:-5] + ".pdf")
    end = "true"
    return end

#图片转文字
@app.get("/img2text", summary='图片转文字', tags=['图片转文字'])
def img2text(path: str = None):
    #print(path)
    #调库进行转换
    image = Image.open(path)
    text = pytesseract.image_to_string(image, lang='chi_sim')
    #print(text)
    file_handle = open(path.split(".")[0]+".txt", mode='w')
    file_handle.write(text)
    file_handle.close()
    return text



if __name__ == '__main__':
    uvicorn.run("main:app", host='0.0.0.0', port=9093)