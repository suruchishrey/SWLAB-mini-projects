from django.shortcuts import render
from django.http import HttpResponse
import pandas as pd
import numpy as np
import pickle
# Create your views here.

'''def index(request):
    return HttpResponse("Hello, world!")
'''
def index(request):

    if request.method == "GET":

        return render(request,"home/index.html")
    
    else:

        battery_power = request.POST.get("battery_power")
        blue = request.POST.get("blue")
        clock_speed = request.POST.get("clock_speed")
        dual_sim = request.POST.get("dual_sim")
        fc = request.POST.get("fc")
        four_g = request.POST.get("four_g")
        int_memory = request.POST.get("int_memory")
        m_dep = request.POST.get("m_dep")
        mobile_wt = request.POST.get("mobile_wt")
        n_cores = request.POST.get("n_cores")
        pc = request.POST.get("pc")
        px_height = request.POST.get("px_height")
        px_width = request.POST.get("px_width")
        ram = request.POST.get("ram")
        sc_h = request.POST.get("sc_h")
        sc_w = request.POST.get("sc_w")
        talk_time = request.POST.get("talk_time")
        three_g = request.POST.get("three_g")
        touch_screen = request.POST.get("touch_screen")
        wifi = request.POST.get("wifi")

        print(int(battery_power))
        print(int(blue))
        print(float(clock_speed))
        print(int(dual_sim))
        print(int(fc))
        print(int(four_g))
        print(int(int_memory))
        print(float(m_dep))
        print(int(mobile_wt))
        print(int(n_cores))
        print(int(pc))
        print(int(px_height))
        print(int(px_width))
        print(int(ram))
        print(int(sc_h))
        print(int(sc_w))
        print(int(talk_time))
        print(int(three_g))
        print(int(touch_screen))
        print(int(wifi))

        filename = "F:/DjangoProjects/MobilePricePredictor/home/models/finalized_model_mobile.sav"
        price=""
        # 0 (low cost), 1 (medium cost), 2 (high cost) and 3 (very high cost)
        loaded_model = pickle.load(open(filename, 'rb'))
        new_prediction = loaded_model.predict((np.array([[int(battery_power),int(blue),float(clock_speed),int(dual_sim),int(fc),int(four_g),int(int_memory),float(m_dep),int(mobile_wt),int(n_cores),int(pc),int(px_height),int(px_width),int(ram),int(sc_h),int(sc_w),int(talk_time),int(three_g),int(touch_screen),int(wifi)]])))
        if new_prediction == 0:
            price = "low cost"
        elif new_prediction == 1:
            price = "medium cost"
        elif new_prediction == 2:
            price = "high cost"
        else:
            price = "very high cost"
        print("Prediction price:" , price)

        return render(request,"home/index.html",{'price':price})