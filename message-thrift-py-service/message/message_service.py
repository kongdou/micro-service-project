# coding: utf-8
from api import MessageService
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import smtplib
from email.mime.text import MIMEText
from email.header import Header
sender = "henuxiaojie@126.com"
authCode = 'deepsky123'

class MessageServiceHandler:

    def sendMobileMessage(self, mobile, message):
        print ("sendMobileMessage,mobile:"+mobile+",message:"+message)
        return True

    def sendMailMessage(self, email, message):

        print ("sendEmailMessage,mobile:"+email+",message:"+message)
        messageObj = MIMEText(message,'plain','utf-8')
        messageObj['From'] = sender
        messageObj['To'] = email
        messageObj['Subject'] = Header('我的邮件','utf-8')

        try:
            smtpObj = smtplib.SMTP('smtp.126.com')
            smtpObj.login(sender,authCode)
            smtpObj.sendmail(sender,[email],messageObj.as_string())
            print ("send mail success")
            return True
        except smtplib.SMTPException:
            print ("send mail failed")
            return False

if __name__ == '__main__':
    handler = MessageServiceHandler()
    processor = MessageService.Processor(handler)
    transport = TSocket.TServerSocket(host='127.0.0.1',port=9090)
    tfactory = TTransport.TFramedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor,transport,tfactory,pfactory)
    print ("python thrift server start")
    server.serve()
    print ("python thrift server exit")