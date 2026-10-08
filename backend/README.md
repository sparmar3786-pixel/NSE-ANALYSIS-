# Angel One backend

Set ANGEL_API_KEY, ANGEL_CLIENT_CODE, ANGEL_PIN, and ANGEL_TOTP_SECRET as server environment variables.

Run:

    pip install -r requirements.txt
    uvicorn backend.app:app --host 0.0.0.0 --port 8000

Endpoints:
- GET /health
- POST /v1/angel/login
- POST /v1/angel/ltp?exchange=NSE&tradingsymbol=...&symboltoken=...

The Android APK must call this backend; credentials must never be embedded in the APK.
