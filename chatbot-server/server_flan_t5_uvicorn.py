import subprocess
import sys

# --- Automatic dependency installation ---
def install(package):
    subprocess.check_call([sys.executable, "-m", "pip", "install", package])

required_packages = ["transformers", "torch", "fastapi", "uvicorn"]

missing_packages = []
for package in required_packages:
    try:
        __import__(package)
    except ImportError:
        missing_packages.append(package)

if missing_packages:
    print(f"Installing missing packages: {', '.join(missing_packages)}")
    for package in missing_packages:
        install(package)

# --- Imports (safe after installation) ---
from fastapi import FastAPI
from pydantic import BaseModel
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
import torch
import uvicorn

# --- Load model ---
model_name = "google/flan-t5-base"
print("Loading model...")
try:
    tokenizer = AutoTokenizer.from_pretrained(model_name)
    model = AutoModelForSeq2SeqLM.from_pretrained(model_name)
except Exception as e:
    print("Error loading the model:", e)
    print("Make sure you have an internet connection the first time to download the model.")
    sys.exit(1)
print("Model loaded successfully!")

# --- FastAPI setup ---
app = FastAPI(title="Flan-T5-Base API")

class RequestPayload(BaseModel):
    message: str

@app.post("/chat")
async def chat(request: RequestPayload):
    inputs = tokenizer(request.message, return_tensors="pt")
    output_ids = model.generate(
        **inputs, max_new_tokens=100, do_sample=True, temperature=0.7
    )
    generated_text = tokenizer.decode(output_ids[0], skip_special_tokens=True)
    return {"response": generated_text}

# --- Start server programmatically ---
if __name__ == "__main__":
    print("Starting FastAPI server at http://127.0.0.1:8000")
    uvicorn.run(app, host="127.0.0.1", port=8000)
