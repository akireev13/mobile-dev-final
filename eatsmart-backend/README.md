# EatSmart Backend

A Django backend with OpenAI integration for generating AI responses.

## Setup

1. **Activate virtual environment:**

   ```bash
   source venv/bin/activate
   ```

2. **Install dependencies:**

   ```bash
   pip install -r requirements.txt
   ```

3. **Set up OpenAI API Key:**

   Create a `.env` file in the project root:

   ```bash
   echo "OPENAI_API_KEY=your-openai-api-key-here" > .env
   ```

4. **Run migrations:**

   ```bash
   python manage.py migrate
   ```

5. **Start the server:**
   ```bash
   python manage.py runserver
   ```

## API Endpoints

### POST /api/generate/

Generate AI response using OpenAI.
