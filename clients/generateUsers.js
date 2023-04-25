const uuid4 = require("uuid4");

const { User, Device } = require("./entities");

const device = new Device("123", uuid4());
const user = new User("a", "a", "a");

console.log(user.connectToDevice(uuid4()));

console.log(device.getStatus(uuid4()));
