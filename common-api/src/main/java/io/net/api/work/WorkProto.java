// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: work/work.proto

package io.net.api.work;

public final class WorkProto {
  private WorkProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_work_Menu_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protobuf_work_Menu_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protobuf_work_Menu_MenuEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protobuf_work_Menu_MenuEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017work/work.proto\022\rprotobuf.work\032\nbase.p" +
      "roto\032\033google/protobuf/empty.proto\"`\n\004Men" +
      "u\022+\n\004menu\030\001 \003(\0132\035.protobuf.work.Menu.Men" +
      "uEntry\032+\n\tMenuEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005valu" +
      "e\030\002 \001(\t:\0028\0012g\n\013WorkService\022\037\n\006doWork\022\t.G" +
      "roupCmd\032\n.MsgResult\0227\n\010manifest\022\026.google" +
      ".protobuf.Empty\032\023.protobuf.work.MenuB\'\n\017" +
      "io.net.api.workB\tWorkProtoP\001\240\001\001\242\002\003DSPb\006p" +
      "roto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          io.net.api.BaseProto.getDescriptor(),
          com.google.protobuf.EmptyProto.getDescriptor(),
        });
    internal_static_protobuf_work_Menu_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protobuf_work_Menu_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protobuf_work_Menu_descriptor,
        new java.lang.String[] { "Menu", });
    internal_static_protobuf_work_Menu_MenuEntry_descriptor =
      internal_static_protobuf_work_Menu_descriptor.getNestedTypes().get(0);
    internal_static_protobuf_work_Menu_MenuEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protobuf_work_Menu_MenuEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    io.net.api.BaseProto.getDescriptor();
    com.google.protobuf.EmptyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
