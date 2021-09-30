# toppan
Statistics and analysis of the use of the Toppan CP500 printer.

Пока нарабатываю навыки, ознакамливаюсь..

Ребят немного поправлю вот так будет получение ip удаленного клиента в C#
C#Выделить код
1
2
string MYIpClient ;
MYIpClient = Convert.ToString(((System.Net.IPEndPoint)client.Client.RemoteEndPoint).Address);




А этой командой Вы получите айпишник сервера т.е. локальную точку.
IpClient = ((IPEndPoint)Client.Client.LocalEndPoint).Address;

Добавлено через 17 минут
Вот так более полное представление:


C#Выделить код
1
2
3
string MYIpClient ;
TcpClient client = client_obj as TcpClient;
MYIpClient = Convert.ToString(((System.Net.IPEndPoint)client.Client.RemoteEndPoint).Address);
