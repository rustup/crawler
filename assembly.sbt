import AssemblyKeys._


assemblySettings


jarName in assembly := "cpuinfo.jar"


test in assembly := {}


mainClass in assembly := Some( "MainApp")


assemblyOption in packageDependency ~= { _.copy(appendContentHash = true) }


