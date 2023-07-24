import tinytuya
import colorsys
import time
import json
from flask import Flask
from flask import request
import os.path

my_path = os.path.abspath(os.path.dirname(__file__))

f = open(str(my_path)+"/remoteCredentials.txt", "r")
device_id = f.read()

c = tinytuya.Cloud()
ir_cmd = {
    "control":"send_ir",
    "head":"010ed20000000000040015004000ad0730",
    "key1":"002$$0020E0E0E01F@%",
    "type":0,
    "delay":300
}
cloud_cmd = {
    "commands": [
        {
            "code": "ir_send",
            "value": json.dumps(ir_cmd)
        },
    ]
}
c.sendcommand(device_id, cloud_cmd)
remote_list = c.cloudrequest( '/v2.0/infrareds/' + device_id + '/remotes' )
remote_id = remote_list['result'][0]['remote_id'] # Grab the first remote for this example
remote_key_list = c.cloudrequest( '/v2.0/infrareds/%s/remotes/%s/keys' % (device_id, remote_id) )

app = Flask(__name__)
@app.route("/")
def run_command():
    key = request.args.get('key')
    post_data = {
    "key": key, #"Power",
    "category_id": remote_key_list['result']['category_id'],
    "remote_index": remote_key_list['result']['remote_index']
    }
    c.cloudrequest( '/v2.0/infrareds/%s/remotes/%s/command' % (device_id, remote_id), post=post_data )
    return 'Executed'

if __name__ == '__main__':
    app.run(debug=False, port=9989)