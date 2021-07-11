import os
import socket
import logging
import threading

IP = '192.168.14.82'
PORT = 678
PORT2 = 6789
SOCKET_TIMEOUT = 100000


def update_the_info(client_socket):
    f = open("client_info", "a")
    info = f.read()
    print("send")
    print(info)
    b = bytes(info, 'utf-8')
    client_socket.send(b)


def save_the_info(client_request):
    info = client_request[6::]
    info = info.decode('utf-8')
    f = open("client_info", "a")
    f.write(info)
    f.close()
    print("save")
    print(info)


def update_the_info2(client_socket):
    f = open("client2_info", "a")
    info = f.read()
    print("send")
    print(info)
    b = bytes(info, 'utf-8')
    client_socket.send(b)


def save_the_info2(client_request):
    info = client_request[6::]
    info = info.decode('utf-8')
    f = open("client2_info.txt", "a")
    f.write(info)
    f.close()
    print("save")
    print(info)


def send_the_info(client_socket):
    f = open("client_info", "r")
    info = f.read()
    f2 = open("client2_info", "r")
    info2 = f2.read()
    if info < info2:
        client_socket.send(info2)


def send_the_info2(client_socket):
    f = open("client_info", "r")
    info = f.read()
    f2 = open("client2_info", "r")
    info2 = f2.read()
    if info > info2:
        client_socket.send(info)


def socket_handle(server_socket):
    server_socket.listen(10)
    print("Listening for connections on port %d" % PORT)
    while True:
        client_socket, client_address = server_socket.accept()
        client_socket.settimeout(SOCKET_TIMEOUT)
        print('Client connected')
        sos = client_socket.recv(2)
        s = client_socket.recv(int(sos))
        client_request = ''
        if int(sos) > 3:
            counter = int(s)
            while counter > 0:
                client_request += client_socket.recv(1000)
                counter -= 1000
        else:
            client_request = client_socket.recv(int(s))
        if client_request[2:6] == b'POST':
            print('Want to send his information')
            save_the_info(client_request)
        elif client_request[2:5] == b'GET':
            print('Want information for first time')
            update_the_info(client_socket)
        elif client_request[2:6] == b'GETU':
            print('check information')
            send_the_info(client_socket)
        else:
            print('Error: Not a valid request')
        client_socket.close()


def thread_function(server_socket):
    server_socket.listen(10)
    print("Listening for connections on port %d" % PORT2)
    while True:
        client_socket, client_address = server_socket.accept()
        client_socket.settimeout(SOCKET_TIMEOUT)
        print('Client connected')
        client_request = client_socket.recv(1024)
        if client_request[2:6] == b'POST':
            print('Want to send his information')
            save_the_info2(client_request)
        elif client_request[2:5] == b'GET':
            print('Want information for first time')
            update_the_info2(client_socket)
        elif client_request[2:6] == b'GETU':
            print('check information')
            send_the_info2(client_socket)
        else:
            print('Error: Not a valid request')
        client_socket.close()


def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((IP, PORT))
    server_socket2.bind((IP, PORT2))
    nothing = ""
    need_update = False
    first = False
    while True:
        threading.Thread(target=thread_function, args=(server_socket2,)).start()
        socket_handle(server_socket)



if __name__ == "__main__":
    main()
