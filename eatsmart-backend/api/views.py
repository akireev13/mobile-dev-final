from django.shortcuts import render
import json
import os
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.conf import settings
from openai import OpenAI
from .resources.system_prompt import system_prompt
from .resources.schemas import MealPlanSchema
# Create your views here.

@csrf_exempt
@require_http_methods(["POST"])
def generate(request):
    try:
        data = request.body
        if not data:
            return JsonResponse({
                'error': 'No data provided'
            }, status=400)
        data = data.decode('utf-8')
        api_key = getattr(settings, 'OPENAI_API_KEY', None)
        if not api_key:
            api_key = os.getenv('OPENAI_API_KEY')
        if not api_key:
            return JsonResponse({
                'error': 'OpenAI API key not configured. Please set OPENAI_API_KEY in settings or environment variables.'
            }, status=500)
        
        client = OpenAI(api_key=api_key)
        
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": data}
            ],
            
        )
        
        generated_text = json.loads(response.choices[0].message.content)
        print(generated_text)
        return JsonResponse(generated_text, status=200)
    
    except json.JSONDecodeError:
        return JsonResponse({
            'error': 'Invalid JSON in request body'
        }, status=400)
    except Exception as e:
        return JsonResponse({
            'error': f'An error occurred: {str(e)}'
        }, status=500)
