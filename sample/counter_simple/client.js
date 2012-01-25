var server = Octopus.getServer();
count = server.counter + 1;
document.write("I'm " + count + "th visitor!");
server.counter = count;

