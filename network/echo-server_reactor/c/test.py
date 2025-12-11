import socket
import threading
import time
from concurrent.futures import ThreadPoolExecutor

SERVER_IP   = '127.0.0.1'
PORT        = 12345
NUM_CLIENTS = 1000
TIMEOUT     = 5
BUFFER_SIZE = 1024

success_count = 0
failure_count = 0
lock = threading.Lock()

def client_test(client_id):
    global success_count, failure_count

    message = f"Hello from client {client_id}!"

    try:
        client_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client_sock.settimeout(TIMEOUT)
        client_sock.connect((SERVER_IP, PORT))

        client_sock.sendall(message.encode('utf-8'))

        received_data = client_sock.recv(BUFFER_SIZE).decode('utf-8')

        if received_data == message:
            with lock:
                success_count += 1
        else:
            with lock:
                failure_count += 1
        
        client_sock.close()
    
    except ConnectionRefusedError:
        with lock:
            failure_count += 1
        print(f"Client {client_id}: FAILED - Connection Refused")

    except socket.timeout:
        with lock:
            failure_count += 1
        print(f"Client {client_id}: FAILED - Timeout occured.")
    except Exception as e:
        with lock:
            failure_count += 1
        print(f"Client {client_id}: Unexpected Error: {e}")

if __name__ == "__main__":
    print(f"Starting Load Test: {NUM_CLIENTS} concurrent clients")

    start_time = time.time()

    with ThreadPoolExecutor(max_workers=NUM_CLIENTS) as executor:
        executor.map(client_test, range(1, NUM_CLIENTS + 1))
    
    end_time = time.time()

    print("\nTest Results")
    print(f"Elapsed time: {end_time - start_time:.2f} seconds")
    print(f"Total attempts: {NUM_CLIENTS}")
    print(f"Successful echoes: {success_count}")
    print(f"Failed connections/echoes: {failure_count}")

    if failure_count == 0:
        print("All clients connected and echoed successfully")