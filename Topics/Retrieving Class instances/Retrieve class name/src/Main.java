/**
 Get name of the class for the object provided.
 */
class ClassGetter {

    public String getObjectClassName(Object object) {
        System.out.println(Object.class.getSuperclass());
        return object.getClass().getName();

    }

}