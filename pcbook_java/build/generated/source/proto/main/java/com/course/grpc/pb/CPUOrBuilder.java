// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: processor_message.proto

package com.course.grpc.pb;

public interface CPUOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CPU)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string brand = 1;</code>
   * @return The brand.
   */
  java.lang.String getBrand();
  /**
   * <code>string brand = 1;</code>
   * @return The bytes for brand.
   */
  com.google.protobuf.ByteString
      getBrandBytes();

  /**
   * <code>string name = 2;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>uint32 num_cores = 3;</code>
   * @return The numCores.
   */
  int getNumCores();

  /**
   * <code>uint32 num_threads = 4;</code>
   * @return The numThreads.
   */
  int getNumThreads();

  /**
   * <code>double min_ghz = 5;</code>
   * @return The minGhz.
   */
  double getMinGhz();

  /**
   * <code>double max_ghz = 6;</code>
   * @return The maxGhz.
   */
  double getMaxGhz();
}