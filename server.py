import socket
import threading

IP = '192.168.43.75'
PORT = 678
PORT2 = 6789
SOCKET_TIMEOUT = 100000


def save_the_info(client_request):
    f = open("client_info", "ab+")
    newFileByteArray = bytearray(client_request)
    f.write(newFileByteArray)
    # f.write()
    f.close()
    print("save")
    print(client_request)


def save_the_info2(client_request):
    f = open("client_info2", "ab+")
    newFileByteArray = bytearray(client_request)
    f.write(newFileByteArray)
    # f.write()
    f.close()
    print("save")
    print(client_request)


def send_the_info(client_socket):
    f = open("client_info", "rb")
    info = f.read()
    if len(info) > 0:
        client_socket.send(info)



def socket_handle(server_socket):
    server_socket.listen(10)
    print("Listening for connections on port %d" % PORT)
    while True:
        client_socket, client_address = server_socket.accept()
        client_socket.settimeout(SOCKET_TIMEOUT)
        print('Client connected')
        bytesize = 0

        for i in reversed(range(4)):
            b =  client_socket.recv(1)[0]
            bytesize = bytesize + b*(256**i)
            print(b, i)
            print(bytesize)

        client_socket.recv(2)
        post = client_socket.recv(4)
        print(post)

        if post == b'POST':
            print('Want to send his information')
            client_request = client_socket.recv(bytesize)
            print(client_request)
            save_the_info(client_request)
        elif post == b'GETU':
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
        bytesize = 0

        for i in reversed(range(4)):
            b =  client_socket.recv(1)[0]
            bytesize = bytesize + b*(256**i)
            print(b, i)
            print(bytesize)

        client_socket.recv(2)
        post = client_socket.recv(4)
        print(post)

        if post == b'POST':
            print('Want to send his information')
            client_request = client_socket.recv(bytesize)
            print(client_request)
            save_the_info2(client_request)
        elif post == b'GETU':
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