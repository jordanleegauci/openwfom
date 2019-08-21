import psutil, os, json, time
from pywinauto.application import Application
from shutil import copyfile
from datetime import datetime

class Andor():

    def __init__(self):
        pass

    def create_camera_file_folder(mouse):
        date = str(datetime.now())[:10]
        cpu_name = os.environ['COMPUTERNAME']
        # Check to make sure the proper computer is being used
        print("This computer's name is "+cpu_name)
        if cpu_name == "DESKTOP-TFJIITU":
            path = "S:/WFOM/data/"
        else:
            path = "C:/WFOM/data/"
            check = input("It looks like you are not using a designated work station. Would you like to continue? (y/n) ")
            if check == "y":
                pass
            else:
                exit()
        with open("JSPLASSH/archive.json", "r+") as f:
            archive = json.load(f)
            d = archive["mice"][mouse]["last_trial"]+1
            archive["mice"][mouse]["last_trial"] = d
            f.seek(0)
            json.dump(archive, f, indent=4)
            f.truncate()
        f.close()
        if not os.path.isdir(path + mouse + "_" + str(d)):
            path = path + mouse + "_" + str(d)
        else:
            path = path + mouse + "_" + str(d+1)
        print("Making directory: "+path)
        os.mkdir(path)
        print("Making directory: "+path+"/CCD")
        os.mkdir(path+"/CCD")
        print("Making directory: "+path+"/auxillary")
        os.mkdir(path+"/auxillary")
        print("Making directory: "+path+"/webcam")
        os.mkdir(path+"/webcam")
        return path

    def deploy_settings(path):
        print("Copying JSPLASSH/settings.json to "+path+"/settings.json")
        src = "JSPLASSH/settings.json"
        dst = path+"/settings.json"
        copyfile(src, dst)
        return dst

    def set_parameters():
        cwd = os.getcwd()
        set_param = "resources\solis_scripts\set_parameters"
        spra_deaux = "resources\solis_scripts\SPRA_deaux"

        files = '"%s\%s" "%s\%s"' % (cwd, set_param, cwd, spra_deaux)

        app = Application().connect(title_re="Andor")
        andor = app.window(title_re="Andor")
        andor.menu_select("File->Open")
        open = app.window(title_re="Open")
        file_name = open.Edit.set_text(files)
        open.ComboBox3.select('Andor Program Files  (*.pgm)')
        time.sleep(1)
        open.Button.click()
        andor.menu_select("File -> Run Program")
