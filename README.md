# toppan
Statistics and analysis of the use of the Toppan CP500 printer.

Пока нарабатываю навыки, ознакамливаюсь..

Немного поправлю вот так будет получение ip удаленного клиента в 
Выделить код

string MYIpClient ;
MYIpClient = Convert.ToString(((System.Net.IPEndPoint)client.Client.RemoteEndPoint).Address);

А этой командой Вы получите айпишник сервера т.е. локальную точку.
IpClient = ((IPEndPoint)Client.Client.LocalEndPoint).Address;

Вот так более полное представление:

string MYIpClient ;
TcpClient client = client_obj as TcpClient;
MYIpClient = Convert.ToString(((System.Net.IPEndPoint)client.Client.RemoteEndPoint).Address);
