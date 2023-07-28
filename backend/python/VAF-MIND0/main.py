from transformers import pipeline
from fastapi import FastAPI
import uvicorn

#引用各种模型
#根据正文信息回答问题
question_answerer = pipeline('question-answering')
#文本生成
textGenerator = pipeline("text-generation")
#摘要提取
summarizer = pipeline("summarization")

app = FastAPI(title="VAF-MIND0")

@app.get("/qa", summary='文本问答', tags=['文本相关'])
def qa(text: str = None, q_text: str = None):
    result = question_answerer({'question': q_text, 'context': text})
    result = result['answer']
    return result

@app.get("/tg", summary='文本生成', tags=['文本相关'])
def qa(text: str = None):
    result = textGenerator(text)
    return result[0]['generated_text']

@app.get("/use", summary='问答并生成', tags=['文本相关'])
def qa(text: str = None, q_text: str = None):
    result = question_answerer({'question': q_text, 'context': text})
    result = result['answer']
    result = textGenerator(result)[0]['generated_text']
    return result

#摘要提取 30-100字
@app.get("/sum", summary='摘要提取', tags=['文本相关'])
def sum(text: str = None):
    result = summarizer(text, max_length=100, min_length=30, do_sample=False)
    return result[0]['summary_text']


if __name__ == '__main__':
    uvicorn.run("main:app", host='0.0.0.0', port=9091)