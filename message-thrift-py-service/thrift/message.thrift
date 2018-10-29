namespace java fun.deepsky.docker.thrift.message
namespace py message.api

service MessageService{

    bool sendMobileMessage(1:string mobile,2:string message);

	bool sendMailMessage(1:string email,2:string message);

}

