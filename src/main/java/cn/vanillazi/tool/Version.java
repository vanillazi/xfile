package cn.vanillazi.tool;

public record Version(int major,int minor,int patch) {

    public static Version from(String versionName){
        var items=versionName.split("\\.");
        return new Version(Integer.parseInt(items[0]),Integer.parseInt(items[1]),Integer.parseInt(items[2]));
    }

    public boolean isCompatible(Version version){
        return major== version.major() && minor== version.minor();
    }

    public boolean isLessThen(Version version){
        return major<version.major || (major== version.major && minor < version.minor) ||
                (major== version.major && minor == version.minor && patch< version.patch);
    }
}
